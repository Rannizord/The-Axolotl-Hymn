package me.mrgaabriel.axolotlhymn.commands.vanilla

import com.google.gson.*
import me.mrgaabriel.axolotlhymn.*
import me.mrgaabriel.axolotlhymn.commands.*
import me.mrgaabriel.axolotlhymn.data.*
import me.mrgaabriel.axolotlhymn.utils.*
import net.dv8tion.jda.core.entities.*
import java.io.*

class ReloadCommand : AbstractCommand(
        "reload",
        "Recarrega o bot",
        "função"
) {

    override fun run(message: Message, args: Array<String>) {
        val arg0 = args[0].toLowerCase()

        if (arg0 == "commands") {
            AxolotlHymnLauncher.hymn.loadCommands()

            message.channel.sendMessage("${message.author.asMention} Comandos recarregados com sucesso!").queue()
            return
        }

        if (arg0 == "config") {
            val file = File("config.json")
            val config = Gson().fromJson(file.readText(Charsets.UTF_8), HymnConfig::class.java)

            AxolotlHymnLauncher.hymn.config = config
            message.channel.sendMessage("${message.author.asMention} Configuração recarregada com sucesso!").queue()

            return
        }

        val file = File("config.json")
        val config = Gson().fromJson(file.readText(Charsets.UTF_8), HymnConfig::class.java)
        AxolotlHymnLauncher.hymn.config = config

        AxolotlHymnLauncher.hymn.loadCommands()

        message.channel.sendMessage("${message.author.asMention} Bot recarregado com sucesso!").queue()
    }
}