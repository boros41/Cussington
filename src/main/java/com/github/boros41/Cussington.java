package com.github.boros41;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Cussington {
    static String[] cussWords = {"fuck", "bitch", "ass", "asshole", "faggot", "fag"};

    public static void main(String[] args) {
        final String token = System.getenv("CussingtonToken");

        // log the bot in
        DiscordApi api = logBotIn(token);

        // display black listed cuss words as an ephemeral Embed
        showCussWords(api);
    }

    // log the bot in and return the DiscordApi object used to configure the bot
    public static DiscordApi logBotIn(String token) {
        return new DiscordApiBuilder()
                .setToken(token)
                .setAllIntents()
                .login()
                .join();
    }

    public static void showCussWords(DiscordApi api) {
        // add listener to slash command /cuss
        api.addSlashCommandCreateListener(event -> {
            // get the SlashCommandInteraction to get the slash command's name later
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();

            // check if command name is cuss for /cuss
            if (interaction.getFullCommandName().equals("cuss")) {
                // to store the cuss words in the cussWords[] array as a String
                StringBuilder sb = new StringBuilder();

                // fill the StringBuilder with the cuss words
                for (String cussWord : cussWords) {
                    sb.append(cussWord)
                            .append(System.lineSeparator()); // platform independent new line
                }

                // an event has happened (/cuss), we must respond immediately with the black listed cuss words
                interaction.createImmediateResponder()
                        .addEmbed(new EmbedBuilder().setTitle("Blacklisted Cuss Words").setDescription(sb.toString()))
                        .setFlags(MessageFlag.EPHEMERAL) // ephemeral messages are only seen by the invoker or target user
                        .respond(); // respond within 3 seconds
            }
        });
    }
}
