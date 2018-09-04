package me.ladyproxima.customunlockcodes;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.logging.Logger;

public class CustomUnlockCodes extends JavaPlugin {

    Permission perms = null;

    Logger logger;

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        logger = getLogger();

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        config.addDefault("locked_group_name", "default");
        config.addDefault("unlocked_group_name", "user");
        config.options().copyDefaults(true);
        saveConfig();

        logger.info("Enabled CustomUnlockCodes!");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (perms.playerInGroup(p, config.getString("locked_group_name"))) {

            String code = getSHA(p.getAddress().getHostName()).substring(0, 5);
            if (args.length == 1 && args[0].equals(code)) {
                perms.playerAddGroup(p, config.getString("unlocked_group_name"));
                sendNice(p, "Gratulation! Du wurdest freigeschalten.");

            } else {
                sendNice(p, "Gebrauchsweise: /freischalten <Code den du in den Regeln findest>");
            }
        } else {
            sendNice(p, "Du bist bereits freigeschalten!");
        }

        return true;
    }

    @Override
    public void onDisable() {

    }


    public void sendNice(Player target, String message) {
        target.sendMessage("[" + ChatColor.GOLD + "Freischaltung" + ChatColor.WHITE + "] " + ChatColor.AQUA + message);
    }


    private static String getSHA(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
