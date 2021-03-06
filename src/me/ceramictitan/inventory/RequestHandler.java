package me.ceramictitan.inventory;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.google.common.collect.HashBiMap;

public class RequestHandler {

    public static HashBiMap<String, String> requests = HashBiMap.create();

    public void sendRequest(Player requester, Player target){
	if(requester == target){
	    return;
	}
	if(!requests.containsKey(target.getName()) || !requests.containsValue(target.getName()) && !requests.containsKey(requester.getName()) || !requests.containsValue(requester.getName())){
	    requests.put(requester.getName(), target.getName());
	    requester.sendMessage("[DEBUG] added players to hashmap");
	}
    }
    public boolean hasRequest(Player target){
	if(target == null){
	    return false;
	}
	if(requests.containsValue(target.getName())){
	    return true;
	}
	return false;
    }
    public Player getRequester(Player target){
	if (target != null) {
	    for (Map.Entry<String, String> entry : requests.entrySet()) {
		if (entry.getValue().equalsIgnoreCase(target.getName())) {
		    return Bukkit.getPlayer(entry.getKey());
		}
	    }
	}

	return null;
    }

    public String getRequesterName(Player target){
	return getRequester(target).getName();
    }
    public void acceptRequest(Player requester, Player target){
	if(target == null){
	    return;
	}
	if(requester == null){
	    return;
	}
	Inventory inv = target.getInventory();
	requester.closeInventory();
	requester.openInventory(inv).;
	clearUsers(requester, target);
    }
    public void denyRequest(Player requester, Player target){
	requester.sendMessage(target.getName()+ " declined your request!");
	target.sendMessage("Declined "+requester.getName()+"'s request!");
	clearUsers(requester, target);
    }
    public static void clearUsers(Player requester, Player target){
	if(requests.containsKey(target.getName()) || requests.containsValue(target.getName()) && requests.containsKey(requester.getName()) || requests.containsValue(requester.getName())){
	    requests.remove(requester.getName());
	    requests.remove(target.getName());
	}
    }
    //Debug
    public void isInHashMap(CommandSender sender){
	if(requests.containsKey(sender.getName())){
	    sender.sendMessage("You are in the hashmap");
	}else{
	    sender.sendMessage("You are not in the hashmap");
	}
    }
    public void checkUserInHashmap(CommandSender sender, Player target){
	if(requests.containsValue(target.getName())){
	    sender.sendMessage(target.getName()+" is in the hashmap");
	}else{
	    sender.sendMessage(target.getName()+" is not in the hashmap");
	}
    }
    public void clearUser(CommandSender sender){
	requests.remove(sender.getName());
    }
    public void clearRequests(){
	requests.clear();
    }
}	
