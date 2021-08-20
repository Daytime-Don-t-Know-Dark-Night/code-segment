package com.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Socket socket;
	private static boolean connection_state = false;

	public static void main(String[] args) {
		connect();

		if (connection_state) {
			new Thread(new Client_listen(socket)).start();
			new Thread(new Client_send(socket)).start();
		}
	}

	// 连接服务器
	public static void connect() {
		try {
			socket = new Socket("127.0.0.1", 9999);
			connection_state = true;
		} catch (IOException e) {
			e.printStackTrace();
			connection_state = false;
		}
	}
}


// 客户端接收服务器传来的消息
class Client_listen implements Runnable {

	private Socket socket;

	Client_listen(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while (true) {
				System.out.println(ois.readObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


// 客户端往服务端发送信息
class Client_send implements Runnable {

	private Socket socket;

	Client_send(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("请输入你要发送的信息");
				String string = scanner.nextLine();
				JSONObject object = new JSONObject();
				object.put("type", "chat");
				object.put("message", string);
				oos.writeObject(object);
				oos.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// 心跳包, 客户端主动发送给服务端, 可以减轻服务端的压力
// 在连接中断之后, 客户端主动发起请求, 再次连接服务器
// test
