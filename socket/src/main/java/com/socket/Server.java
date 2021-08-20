package com.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {

		try {
			System.out.println("Socket服务器开始运行");
			ServerSocket serverSocket = new ServerSocket(9999);

			while (true) {

				// 对每个客户创建新的连接
				Socket socket = serverSocket.accept();
				new Thread(new Server_listen(socket)).start();
				new Thread(new Server_send(socket)).start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

// 监听类
class Server_listen implements Runnable {

	private Socket socket;

	public Server_listen(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			// 通过ois来读取客户端传过来的信息
			while (true) {
				System.out.println(ois.readObject());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}


// 服务器发送数据给客户端
class Server_send implements Runnable {

	private Socket socket;

	public Server_send(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 把数据输出
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("请输入要发送的内容: ");
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