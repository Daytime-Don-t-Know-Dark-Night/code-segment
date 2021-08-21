package com.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static Socket socket;
	public static boolean connection_state = false;

	public static void main(String[] args) {
		connect();
	}

	// 连接服务器
	public static void connect() {
		try {
			socket = new Socket("127.0.0.1", 9999);
			connection_state = true;

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			new Thread(new Client_listen(socket, ois)).start();
			new Thread(new Client_send(socket, oos)).start();
			new Thread(new Client_heart(socket, oos)).start();

		} catch (IOException e) {
			e.printStackTrace();
			connection_state = false;
		}
	}

	// 与服务器掉线之后, 客户端会尝试重新连接
	public static void reconnect() {
		while (!connection_state) {
			System.out.println("正在尝试重新连接...");
			connect();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


// 客户端接收服务器传来的消息
class Client_listen implements Runnable {

	private ObjectInputStream ois;
	private Socket socket;

	Client_listen(Socket socket, ObjectInputStream ois) {
		this.socket = socket;
		this.ois = ois;
	}

	@Override
	public void run() {
		try {
			// ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
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

	private ObjectOutputStream oos;
	private Socket socket;

	Client_send(Socket socket, ObjectOutputStream oos) {
		this.socket = socket;
		this.oos = oos;
	}

	@Override
	public void run() {
		try {
			// ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
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
			try {
				socket.close();
				Client.connection_state = false;
				Client.reconnect();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}

// 心跳包, 客户端主动发送给服务端, 可以减轻服务端的压力
// 在连接中断之后, 客户端主动发起请求, 再次连接服务器
class Client_heart implements Runnable {

	//socket通信中， 接收端会验证传来的oos是否是相同的
	private ObjectOutputStream oos;
	private Socket socket;

	Client_heart(Socket socket, ObjectOutputStream oos) {
		this.socket = socket;
		this.oos = oos;
	}

	@Override
	public void run() {

		try {
			System.out.println("开始心跳包监测");

			// 不能每次使用新的oos, 一次连接中应该使用相同的oos
			// ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			while (true) {
				Thread.sleep(5000);
				JSONObject object = new JSONObject();
				object.put("type", "heart");
				object.put("message", "心跳包");
				oos.writeObject(object);
				oos.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				socket.close();
				Client.connection_state = false;
				Client.reconnect();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

}
