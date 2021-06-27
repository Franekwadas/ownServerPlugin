package ownplugin.myownplugin.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements CommandExecutor, Listener {

	public void onEnable() {
		System.out.println("myOwnPlugin is now active - Welcome!");
		getConfig().addDefault("Author", "uiuiuoisad");
		getConfig().addDefault("wlasciciel", "");
		getConfig().set("chatIsEnable", true);
		getConfig().options().copyDefaults(true);
		getServer().getPluginManager().registerEvents(this, this);
		saveConfig();
		reloadConfig();
	}
	public void onDisable() {
		System.out.println("myOwnPlugin has been deactivated - GoodBye!");
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void BlockBreakeEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/ServerOwnPlugin/autorization.yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
		
		if (yamlFile.get(p.getName() + ".register") == null) {
			p.kickPlayer(ChatColor.RED + "Zosta³ dodany plugin na logowanie! Prosze wejœæ i sie zarejestrowaæ");
		} else if (yamlFile.getBoolean(p.getName() + ".register") == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zarejestruj korzystaj¹c z /register <has³o>");
		} else if (yamlFile.get(p.getName() + ".login") == null) {
			if (yamlFile.getString(p.getName() + ".password") == null) {
				p.kickPlayer(ChatColor.RED + "Z nieoczekiwanego powodu twoje has³o prawdopodobnie zosta³o zrestartowane" + "\n" + "Proszê wejœæ na nowo i siê jeszcze raz zarejestrowaæ.");
			} else {
				p.kickPlayer(ChatColor.RED + "Wyst¹pi³ nieoczekiwany b³¹d" + "\n" + "Proszê wejœæ na nowo.");
			}
		} else if (yamlFile.getBoolean(p.getName() + ".login") == true) {
			
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zalogouj korzystaj¹c z /login <has³o>");
			
		}
		
		File f2 = new File("plugins/ServerOwnPlugin/statystyki.yml");
		YamlConfiguration yamlFile2 = YamlConfiguration.loadConfiguration(f2);
		if (yamlFile2.get(p.getName() + ".bloki") == null) yamlFile2.set(p.getName() + ".bloki", 0);
		yamlFile2.set(p.getName() + ".bloki", yamlFile2.getInt(p.getName() + ".bloki") + 1);
		try {
			yamlFile2.save(f2);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onDead(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		
		File file = new File("plugins/ServerOwnPlugin/statystyki.yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
		
		if (yamlFile.get(p.getName() + ".deads") == null) yamlFile.set(p.getName() + ".deads", 0);
		yamlFile.set(p.getName() + ".deads", yamlFile.getInt(p.getName() + ".deads") + 1);
		try {
			yamlFile.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		File f = new File("plugins/ServerOwnPlugin/autorization.yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
		
		if (yamlFile.get(p.getName() + ".register") == null) {
			p.kickPlayer(ChatColor.RED + "Zosta³ dodany plugin na logowanie! Prosze wejœæ i sie zarejestrowaæ");
		} else if (yamlFile.getBoolean(p.getName() + ".register") == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zarejestruj korzystaj¹c z /register <has³o> ");
		} else if (yamlFile.get(p.getName() + ".login") == null) {
			if (yamlFile.getString(p.getName() + ".password") == null) {
				p.kickPlayer(ChatColor.RED + "Z nieoczekiwanego powodu twoje has³o prawdopodobnie zosta³o zrestartowane" + "\n" + "Proszê wejœæ na nowo i siê jeszcze raz zarejestrowaæ.");
			} else {
				p.kickPlayer(ChatColor.RED + "Wyst¹pi³ nieoczekiwany b³¹d" + "\n" + "Proszê wejœæ na nowo.");
			}
		} else if (yamlFile.getBoolean(p.getName() + ".login") == true) {
			
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zalogouj korzystaj¹c z /login <has³o>");
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/ServerOwnPlugin/autorization.yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
		String password = yamlFile.getString(p.getName() + ".password");
		yamlFile.set(p.getName() + ".tryToLogin", 0);
		if (password == null) {
			yamlFile.set(p.getName() + ".register", true);
			p.sendMessage(ChatColor.GREEN + "Witamy na naszym serwerze! Wpisz komende /register <haslo>  aby siê zarejestrowaæ!");
		} else {
			yamlFile.set(p.getName() + ".login", true);
			p.sendMessage(ChatColor.GREEN + "Witamy na naszym serwerze! Wpisz komende /login <haslo> aby siê zalogowaæ!");
		}
		
		try {
			yamlFile.save(f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMessage(PlayerChatEvent e) {
		Player p = e.getPlayer();
		//sprawdzanie czy gracz jest zalogowany/zarejestrowany
		File f = new File("plugins/ServerOwnPlugin/autorization.yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
		boolean register = yamlFile.getBoolean(p.getName() + ".register");
		boolean login = yamlFile.getBoolean(p.getName() + ".login");
		if (register == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zarejestruj korzystaj¹c z /register <has³o> ");
		} else if (login == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Najpierw sie zalogouj korzystaj¹c z /login <has³o>");
		}
		String msg = e.getMessage();
		if(getConfig().get("chatIsEnable") == null) getConfig().set("chatIsEnable", true);
		if(getConfig().getBoolean("chatIsEnable") == false) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Nie mo¿esz teraz pisaæ poniewa¿ czat jest wy³¹czony!");
		}
		
		if (register != true && getConfig().getBoolean("chatIsEnable") == true) {
		
			if	(p.hasPermission("ownServerPlugin.adminGroup")) {
				e.setCancelled(true);
				String wiadomosc = (ChatColor.DARK_RED + "" + ChatColor.BOLD + "Administrator " + ChatColor.GRAY + p.getName() + ChatColor.AQUA + " >> " + ChatColor.GOLD + msg.toLowerCase());
				wiadomosc = wiadomosc.replace("japierdole", "**********");
				wiadomosc = wiadomosc.replace("suko", "****");
				wiadomosc = wiadomosc.replace("suka", "****");
				wiadomosc = wiadomosc.replace("jebana", "******");
				wiadomosc = wiadomosc.replace("jebany", "******");
				wiadomosc = wiadomosc.replace("kurwa", "*****");
				wiadomosc = wiadomosc.replace("pierdole", "********");
				wiadomosc = wiadomosc.replace("wypierdalaj", "***********");
				wiadomosc = wiadomosc.replace("spierdalaj", "**********");
				wiadomosc = wiadomosc.replace("wykurwiaj", "*********");
				wiadomosc = wiadomosc.replace("chuju", "*****");
				wiadomosc = wiadomosc.replace("kurwo", "*****");
				wiadomosc = wiadomosc.replace("cipo", "****");
				wiadomosc = wiadomosc.replace("pierdolona", "**********");
				wiadomosc = wiadomosc.replace("pierdol", "*******");
				wiadomosc = wiadomosc.replace("rucham", "******");
				wiadomosc = wiadomosc.replace("cipa", "****");
				wiadomosc = wiadomosc.replace("kurwy", "*****");
				Bukkit.broadcastMessage(wiadomosc);
			} else if (p.hasPermission("ownServerPlugin.egzorcystaGroup")){
				e.setCancelled(true);
				String wiadomosc = (ChatColor.AQUA + "" + ChatColor.BOLD + "Egzorcysta " + ChatColor.GRAY + p.getName() + ChatColor.AQUA + " >> " + ChatColor.GRAY + msg.toLowerCase());
				wiadomosc = wiadomosc.replace("japierdole", "**********");
				wiadomosc = wiadomosc.replace("suko", "****");
				wiadomosc = wiadomosc.replace("suka", "****");
				wiadomosc = wiadomosc.replace("jebana", "******");
				wiadomosc = wiadomosc.replace("jebany", "******");
				wiadomosc = wiadomosc.replace("kurwa", "*****");
				wiadomosc = wiadomosc.replace("pierdole", "********");
				wiadomosc = wiadomosc.replace("wypierdalaj", "***********");
				wiadomosc = wiadomosc.replace("spierdalaj", "**********");
				wiadomosc = wiadomosc.replace("wykurwiaj", "*********");
				wiadomosc = wiadomosc.replace("chuju", "*****");
				wiadomosc = wiadomosc.replace("kurwo", "*****");
				wiadomosc = wiadomosc.replace("cipo", "****");
				wiadomosc = wiadomosc.replace("pierdolona", "**********");
				wiadomosc = wiadomosc.replace("pierdol", "*******");
				wiadomosc = wiadomosc.replace("rucham", "******");
				wiadomosc = wiadomosc.replace("cipa", "****");
				wiadomosc = wiadomosc.replace("kurwy", "*****");
				Bukkit.broadcastMessage(wiadomosc);
			} else if (p.hasPermission("ownServerPlugin.twojaStaraGroup")) {
				e.setCancelled(true);
				String wiadomosc = (ChatColor.RED + "" + ChatColor.BOLD + "T" + ChatColor.YELLOW + "" + ChatColor.BOLD + "w" + ChatColor.GREEN + "" + ChatColor.BOLD + "o" + ChatColor.BLUE + "" + ChatColor.BOLD + "j" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "a" + " " + ChatColor.RED + "" + ChatColor.BOLD + "S" + ChatColor.YELLOW + "" + ChatColor.BOLD + "t" + ChatColor.GREEN + "" + ChatColor.BOLD + "a" + ChatColor.BLUE + "" + ChatColor.BOLD + "r" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "a "  + ChatColor.GRAY + p.getName() + ChatColor.AQUA + " >> " + ChatColor.GRAY + msg.toLowerCase());
				wiadomosc = wiadomosc.replace("japierdole", "**********");
				wiadomosc = wiadomosc.replace("suko", "****");
				wiadomosc = wiadomosc.replace("suka", "****");
				wiadomosc = wiadomosc.replace("jebana", "******");
				wiadomosc = wiadomosc.replace("jebany", "******");
				wiadomosc = wiadomosc.replace("kurwa", "*****");
				wiadomosc = wiadomosc.replace("pierdole", "********");
				wiadomosc = wiadomosc.replace("wypierdalaj", "***********");
				wiadomosc = wiadomosc.replace("spierdalaj", "**********");
				wiadomosc = wiadomosc.replace("wykurwiaj", "*********");
				wiadomosc = wiadomosc.replace("chuju", "*****");
				wiadomosc = wiadomosc.replace("kurwo", "*****");
				wiadomosc = wiadomosc.replace("cipo", "****");
				wiadomosc = wiadomosc.replace("pierdolona", "**********");
				wiadomosc = wiadomosc.replace("pierdol", "*******");
				wiadomosc = wiadomosc.replace("rucham", "******");
				wiadomosc = wiadomosc.replace("cipa", "****");
				wiadomosc = wiadomosc.replace("kurwy", "*****");
				Bukkit.broadcastMessage(wiadomosc);
			} else {
				e.setCancelled(true);
				String wiadomosc = (ChatColor.GREEN + "" + ChatColor.BOLD + "Gracz " + ChatColor.GRAY + p.getName() + ChatColor.AQUA + " >> " + ChatColor.GRAY + msg.toLowerCase());
				wiadomosc = wiadomosc.replace("japierdole", "**********");
				wiadomosc = wiadomosc.replace("suko", "****");
				wiadomosc = wiadomosc.replace("suka", "****");
				wiadomosc = wiadomosc.replace("jebana", "******");
				wiadomosc = wiadomosc.replace("jebany", "******");
				wiadomosc = wiadomosc.replace("kurwa", "*****");
				wiadomosc = wiadomosc.replace("pierdole", "********");
				wiadomosc = wiadomosc.replace("wypierdalaj", "***********");
				wiadomosc = wiadomosc.replace("spierdalaj", "**********");
				wiadomosc = wiadomosc.replace("wykurwiaj", "*********");
				wiadomosc = wiadomosc.replace("chuju", "*****");
				wiadomosc = wiadomosc.replace("kurwo", "*****");
				wiadomosc = wiadomosc.replace("cipo", "****");
				wiadomosc = wiadomosc.replace("pierdolona", "**********");
				wiadomosc = wiadomosc.replace("pierdol", "*******");
				wiadomosc = wiadomosc.replace("rucham", "******");
				wiadomosc = wiadomosc.replace("cipa", "****");
				wiadomosc = wiadomosc.replace("kurwy", "*****");
				Bukkit.broadcastMessage(wiadomosc);
	
			}
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("pomoc")) {
			if (!p.hasPermission("serverOwnPlugin.help")) {
				p.sendMessage(ChatColor.RED + "Nie masz permisji do u¿ycia tej komendy!");
				return false;
			}
			p.sendMessage(ChatColor.GOLD + "[======---------- (Pomoc) -------======]");
			p.sendMessage("");
			p.sendMessage(ChatColor.RED + "1. " + ChatColor.GOLD + "pomoc: To co w³aœnie czytasz");
			p.sendMessage(ChatColor.RED + "2. " + ChatColor.GOLD + "msg: Mo¿esz wysy³aæ prywatne wiadomoœci do innych!");
			p.sendMessage(ChatColor.RED + "3. " + ChatColor.GOLD + "statystyki: SprawdŸ statystyki gracza!");
			p.sendMessage("");
			p.sendMessage(ChatColor.GOLD + "[======------------ ( ) ---------======]");
			
			return true;
			
		} else if (command.getName().equalsIgnoreCase("msg")) {
			if (args.length != 0) {
				if (args.length > 1) {
					
					String wiadomosc = "";
					int length = args.length - 1;

					for (int i = 1; i <= length; i++) {
						wiadomosc = wiadomosc + args[i];
						if (i != length) wiadomosc = wiadomosc + " ";
					}
                    
					
					Player userToSendMessage = Bukkit.getServer().getPlayer(args[0]);
					
					if (userToSendMessage.getName() == p.getName()) {
						p.sendMessage(ChatColor.RED + "Nie mo¿esz wys³aæ wiadomoœci sam sobie!");
						return false;
					}
					
					if (userToSendMessage != null) {
						
						userToSendMessage.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " -> " + ChatColor.GOLD + userToSendMessage.getName() + ChatColor.RED + "] " + ChatColor.GRAY + wiadomosc);
						p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " -> " + ChatColor.GOLD + userToSendMessage.getName() + ChatColor.RED + "] " + ChatColor.GRAY + wiadomosc);
						return true;
						
					} else {
						p.sendMessage(ChatColor.RED + "Podaj prawid³owego gracza!");
						return false;
					}
					
				} else {
					p.sendMessage(ChatColor.RED + "Podaj wiadomoœæ!");
					return false;
				}
			} else {
				p.sendMessage(ChatColor.RED + "Musisz podaæ komu chcesz wys³aæ wiadomoœæ!");
				return false;
			}
		} else if (command.getName().equalsIgnoreCase("setowner")) {
			if (p.hasPermission("*") || p.isOp()) {
				getConfig().set("wlasciciel", p.getName());
				saveConfig();
				reloadConfig();
				p.sendMessage(ChatColor.GREEN + "Zmieniono w configu w³aœciciela servera na: " + p.getName());
				return true;
			} else {
				p.sendMessage(ChatColor.RED + "Nie masz permisji do u¿ycia tej komendy!");
				return false;
			}
		} else if (command.getName().equalsIgnoreCase("statystyki")) {
			if (args.length > 0 && args.length < 2) {
				
				File file = new File("plugins/ServerOwnPlugin/statystyki.yml");
				YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
				
				Player user = Bukkit.getServer().getPlayer(args[0]);
				
				p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "X------[" + ChatColor.AQUA + "Statystyki u¿ytkownika " + user.getName() + ChatColor.GOLD + "]------X");
				p.sendMessage("");
				if (yamlFile.get(user.getName() + ".bloki") == null) yamlFile.set(user.getName() + ".bloki", 0);
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD +"Wykopane bloki: " + ChatColor.RED + yamlFile.getInt(user.getName() + ".bloki"));
				if (yamlFile.get(user.getName() + ".deads") == null) yamlFile.set(user.getName() + ".deads", 0);
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Œmierci: " + ChatColor.RED + yamlFile.getInt(user.getName() + ".deads"));
				p.sendMessage("");
				return true;
			} else {
				File file = new File("plugins/ServerOwnPlugin/statystyki.yml");
				YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
				
				p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "X------[" + ChatColor.AQUA + "Statystyki u¿ytkownika " + p.getName() + ChatColor.GOLD + "]------X");
				p.sendMessage("");
				if (yamlFile.get(p.getName() + ".bloki") == null) yamlFile.set(p.getName() + ".bloki", 0);
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD +"Wykopane bloki: " + ChatColor.RED + yamlFile.getInt(p.getName() + ".bloki"));
				if (yamlFile.get(p.getName() + ".deads") == null) yamlFile.set(p.getName() + ".deads", 0);
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Œmierci: " + ChatColor.RED + yamlFile.getInt(p.getName() + ".deads"));
				p.sendMessage("");
				return true;


			}
		} else if (command.getName().equalsIgnoreCase("clear")) {
			
			if (p.hasPermission("ownServerPlugin.chat.clear")) {
				
				int whatNumberIsNow = 0;
				
				while (whatNumberIsNow < 100) {
					Bukkit.broadcastMessage("");
					whatNumberIsNow = whatNumberIsNow + 1;
				}
				
				Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Czat zosta³ wyczyszczony przez " + ChatColor.RED + p.getName());
				
			} else {
				p.sendMessage(ChatColor.RED + "Nie masz permisji do u¿ycia tej komendy!");
				return false;
			}
			
		} else if (command.getName().equalsIgnoreCase("chat")) {
			
			if (!p.hasPermission("ownServerPlugin.chat.on") || !p.hasPermission("ownServerPlugin.chat.off") && !p.hasPermission("ownServerPlugin.chat.menage")) {
				p.sendMessage(ChatColor.RED + "Nie masz permisji do u¿ycia tej komendy!");
				return false;
			}
			
			if (getConfig().get("chatIsEnable") == null) getConfig().set("chatIsEnable", true);
			boolean chat = getConfig().getBoolean("chatIsEnable");
			
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("on")) {
					if (chat == true) {
						p.sendMessage(ChatColor.RED + "Chat jest ju¿ w³¹czony!");
						return false;
					} else {
						chat = true;
						Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Chat zosta³ w³¹czony przez " + ChatColor.RED + p.getName());
						getConfig().set("chatIsEnable", true);
						saveConfig();
						reloadConfig();
						return true;
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (chat == false) {
						p.sendMessage(ChatColor.RED + "Chat jest ju¿ wy³¹czony!");
						return false;
					} else {
						chat = false;
						Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Chat zosta³ wy³¹czony przez " + ChatColor.AQUA + p.getName());
						getConfig().set("chatIsEnable", false);
						saveConfig();
						reloadConfig();
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Argumentem musi byæ on/off");
					return false;
				}
				
			} else {
				p.sendMessage(ChatColor.RED + "Podaj prawid³owy argument <on/off>!");
				return false;
			}
			
		} else if (command.getName().equalsIgnoreCase("register")) {
			
			if (args.length > 0) {
				
				if (args[0].length() >= 5) {
				
					File f = new File("plugins/ServerOwnPlugin/autorization.yml");
					YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
					String password = yamlFile.getString(p.getName() + ".password");
					
					if (password == null) {
						
						int hashCode = args[0].hashCode();
						yamlFile.set(p.getName() + ".password", hashCode);
						
						try {
							yamlFile.save(f);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						yamlFile.set(p.getName() + ".register", false);
						
						try {
							yamlFile.save(f);
							p.kickPlayer(ChatColor.GREEN + "Zosta³eœ zarejestrowany!" + "\n" + "Do³¹cz ponownie i zaloguj siê!");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Jesteœ ju¿ zarejestrowany!");
						return false;
					}
				} else {
					p.sendMessage(ChatColor.RED + "D³ugoœæ has³a musi byæ d³u¿sza ni¿ 4 litery!");
					return false;
				}
				
			} else {
				p.sendMessage(ChatColor.RED + "Musisz podaæ has³o!");
				return false;
			}
			
		} else if (command.getName().equalsIgnoreCase("login")) {
			
			if (args.length > 0) {
				
				File f = new File("plugins/ServerOwnPlugin/autorization.yml");
				YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(f);
				String password = yamlFile.getString(p.getName() + ".password");
				
				Integer hash = args[0].hashCode();
				
				if (yamlFile.get(p.getName() + ".password") != null) {
					
					if (hash.toString().equals(password)) {
						
						yamlFile.set(p.getName() + ".login", false);
						
						try {
							yamlFile.save(f);
							p.sendMessage(ChatColor.GREEN + "Zosta³eœ zalogowany! Mi³ej gry!");
						} catch (IOException e) {
							e.printStackTrace();
							p.kickPlayer(ChatColor.RED + "Przykro mi ale wyst¹pi³ nieoczekiwany b³¹d" + "\n" + "WejdŸ na serwer jeszcze raz i powiedz mamie");
						}
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Podaj prawid³owe has³o!");
						if (yamlFile.get(p.getName() + ".tryToLogin") == null) { 
							yamlFile.set(p.getName() + ".tryToLogin", 1);
							try {
								yamlFile.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							yamlFile.set(p.getName() + ".tryToLogin", yamlFile.getInt(p.getName() + ".tryToLogin", 1) + 1);
							if (yamlFile.getInt(p.getName() + ".tryToLogin") >= 4) {
								p.kickPlayer(ChatColor.RED + "Zbyt du¿a iloœæ prób zalogowañ" + "\n" + "Proszê do³¹czyæ jeszcze raz");
							}
							try {
								yamlFile.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						return false;
					}
					
				} else {
					
					p.sendMessage(ChatColor.RED + "Aby u¿yc tej komendy najpierw musisz siê zarejestrowaæ!");
					
				}
			} else {
				p.sendMessage(ChatColor.RED + "Proszê podaæ has³o!");
				return false;
			}
			
		}
		return false;
	}
	
	
	
	
	
}
