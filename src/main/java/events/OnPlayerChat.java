package events;

import static gui.items.types.TextRequest.StandingBy;
import org.bukkit.event.player.PlayerChatEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerChat {

    public static void OnPlayerChat(PlayerChatEvent e) {
        if (StandingBy.containsKey(e.getPlayer())) {
            StandingBy.get(e.getPlayer()).onTextEntry(e);
        }
    }
}
