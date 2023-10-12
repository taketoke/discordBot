package nekogazo.discordbot1;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class notbot extends ListenerAdapter {
	private static final String TARGET_VOICECHANNNEL_NAME = "bot通知用";
	private static final String JOIN_MESSAGE = "さんが入室したよ！";
	private static final String LEAVE_MESSAGE = "さんが退室しちゃった・・・";
	
	
	public static void main(String[] args) {
		
		try {
			String botToken = System.getenv("DISCORD_BOT_TOKEN");
	        JDABuilder.createDefault(botToken)
            .addEventListeners(new notbot())
            .build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	
	//入室通知メソッド
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        // メンバーがボイスチャンネルに入った場合の処理
        if (!event.getMember().getUser().isBot()) {
        	Guild guild = event.getGuild();
            TextChannel textChannel = guild.getTextChannelsByName
            		(TARGET_VOICECHANNNEL_NAME, true).stream().findFirst().orElse(null); // テキストチャンネルを取得
            if (textChannel != null) {
                textChannel.sendMessage(event.getMember().getAsMention() + JOIN_MESSAGE).queue(); // メッセージを送信
            } else {
                System.out.println("指定されたテキストチャンネルが見つかりませんでした。");
            }
        }
    }
    
    //退室通知メソッド
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        // メンバーがボイスチャンネルから退室した場合の処理
        if (!event.getMember().getUser().isBot()) {
            Guild guild = event.getGuild();
            TextChannel textChannel = guild.getTextChannelsByName(TARGET_VOICECHANNNEL_NAME, true).stream().findFirst().orElse(null);

            if (textChannel != null) {
                textChannel.sendMessage(event.getMember().getAsMention() + LEAVE_MESSAGE).queue(); // メッセージを送信
            } else {
                System.out.println("指定されたテキストチャンネルが見つかりませんでした。");
            }
        }
    }
    

}
