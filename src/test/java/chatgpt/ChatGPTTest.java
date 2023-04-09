package chatgpt;

import com.unfbx.chatgpt.ChatGPTClient;

/**
 * 描述： 测试类
 *
 * @author https:www.unfbx.com
 * 2023-02-11
 */
public class ChatGPTTest {
    public static void main(String[] args) {
        ChatGPTClient client = new ChatGPTClient("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");
        String body = client.askQuestion("简单描述下三体这本书");
        System.out.println(body);
    }
}
