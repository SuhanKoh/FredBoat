package fredboat.audio.player;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class PlayerLimitManager {

    // A negative limit means unlimited
    private static int limit = -1;

    public static boolean checkLimit(Guild guild) {
        GuildPlayer guildPlayer = PlayerRegistry.getExisting(guild);
        //noinspection SimplifiableIfStatement
        if (guildPlayer != null && guildPlayer.getSongCount() > 0)
            return true;

        return limit < 0
                || PlayerRegistry.getPlayingPlayers().size() < limit;

    }

    public static boolean checkLimitResponsive(TextChannel channel) {
        boolean b =  checkLimit(channel.getGuild());

        if (!b) {
            String msg = "FredBoat is currently at maximum capacity! The bot is currently fixed to only play up to `"
                    + limit + "` streams, otherwise we would risk disconnecting from Discord under the network load."
                    + "\nIf you want to help us increase the limit or you want to use our non-overcrowded bot, please "
                    + "support our work on Patreon:"
                    + "\n<https://www.patreon.com/fredboat>"
                    + "\n\nSorry for the inconvenience! You might want to try again later. This message usually only appears at peak time.";
            channel.sendMessage(msg).queue();
        }

        return b;
    }

    public static int getLimit() {
        return limit;
    }

    public static void setLimit(int limit) {
        PlayerLimitManager.limit = limit;
    }
}
