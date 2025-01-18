package com.agrify.emandi.servlets;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.agrify.emandi.dl.user.UserDAOImpl;
import com.agrify.emandi.dl.user.UserDTO;
import com.agrify.emandi.util.Validation;

public class Login extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("********************************");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			System.out.println("email : " + email);
			System.out.println("password : " + password);

			Validation valid = new Validation();
			System.out.println("heelooo");
			boolean emailValid = valid.mailCheck(email);
			boolean passwordValid = valid.validString(password, 30, false);
			System.out.println("emailValid " + emailValid);
			System.out.println("passwordValid " + passwordValid);

			if (emailValid == false || passwordValid == false) {
				try {
					RequestDispatcher rd = request.getRequestDispatcher("/registration.html");
					rd.forward(request, response);
				} catch (Exception ase) {
					System.out.println(ase);
				}
			}
			UserDTO user = new UserDTO();
			user.setEmail(email);
			user.setPassword(password);
			UserDAOImpl userDAO = new UserDAOImpl();
			boolean valid_ = userDAO.validation(user);
			System.out.println(valid_);
			if (valid_ == true) {
				user = userDAO.selectUser(user);
				String user_role = user.getUser_role().toString();
				if (user_role.equals("Buyer")) {
					final Map<String, Object> data = new HashMap<String, Object>();
					data.put("id", user.getId());
					data.put("first_name", user.getFirst_name());
					data.put("last_name", user.getLast_name());
					data.put("birth", user.getBirth());
					data.put("password", user.getPassword());
					data.put("email", user.getEmail());
					data.put("phone_number", user.getPhone_number());
					data.put("aadhar_id", user.getAadhaar_id());
					data.put("user_role", user.getUser_role().toString());

					final JSONObject json_string = new JSONObject(data);

					// Encoding the cookie data into base64 to avoid using unsupported characters
					final String user_data_cookie = Base64.getEncoder()
							.encodeToString((json_string.toString()).getBytes());

					// Cookies accept strings as value so change json to string
					Cookie ck = new Cookie("user_data_cookie", user_data_cookie);
					response.addCookie(ck);
					// response.setHeader("Set-Cookie", "SameSite=Strict");

					RequestDispatcher rd = request
							.getRequestDispatcher("/buyer_profile.html");
					rd.forward(request, response);
				} else if (user_role.equals("Seller")) {
					final Map<String, Object> data = new HashMap<String, Object>();
					data.put("id", user.getId());
					data.put("first_name", user.getFirst_name());
					data.put("last_name", user.getLast_name());
					data.put("birth", user.getBirth());
					data.put("password", user.getPassword());
					data.put("email", user.getEmail());
					data.put("phone_number", user.getPhone_number());
					data.put("aadhar_id", user.getAadhaar_id());
					data.put("user_role", user.getUser_role().toString());

					final JSONObject json_string = new JSONObject(data);

					// Encoding the cookie data into base64 to avoid using unsupported characters
					final String user_data_cookie = Base64.getEncoder()
							.encodeToString((json_string.toString()).getBytes());

					// Cookies accept strings as value so change json to string
					Cookie ck = new Cookie("user_data_cookie", user_data_cookie);
					response.addCookie(ck);
					// response.setHeader("Set-Cookie", "SameSite=Strict");

					RequestDispatcher rd = request
							.getRequestDispatcher("/seller_profile.html");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("/auction_form.html");
					rd.forward(request, response);
				}
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/login.html");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/login.html");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}