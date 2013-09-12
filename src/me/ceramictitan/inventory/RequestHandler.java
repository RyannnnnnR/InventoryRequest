package me.ceramictitan.inventory;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RequestHandler {

    public static Map<String, String> requests = new HashMap<String, String>();

    public void sendRequest(Player requester, Player target){
	if(requester == null){
	    return;
	}
	if(target == null){
	    return;
	}
	if(requester == target){
	    return;
	}
	if(!requests.containsKey(target.getName())){
	    requests.put(requester.getName(), target.getName());
	}
    }
    public boolean hasRequest(Player target){
	if(target == null){
	    return false;
	}
	if(requests.containsKey(target.getName())){
	    return true;
	}
	return false;
    }
    public Player getRequester(Player target){
	if(target == null){
	    return null;
	}
	return Bukkit.getPlayerExact(requests.get(target.getName()));
    }

    public String getRequesterName(Player target){
	return getRequester(target).getName();
    }
    public void acceptRequest(Player requester, Player target){
	Inventory inv = target.getInventory();
	requester.closeInventory();
	requester.openInventory(inv);
	clearUsers(requester, target);
    }
    public void denyRequest(Player requester, Player target){
	requester.sendMessage(target.getName()+ " declined your request!");
	target.sendMessage("Declined "+requester.getName()+"'s request!");
	clearUsers(requester, target);
    }
    public static void clearUsers(Player requester, Player target){
	if(requests.containsKey(requester.getName()) && requests.containsKey(target.getName())){
	    requests.remove(requester.getName());
	    requests.remove(target.getName());
	}
    }
}	