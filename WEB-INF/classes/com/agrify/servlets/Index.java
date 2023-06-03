package com.agrify.servlets;

import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.agrify.dl.user.UserDAOImpl;
import com.agrify.dl.user.UserDTO;
import com.agrify.util.Validation;

/**
 * Index
 */
public class Index extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			javax.servlet.http.Cookie[] ck = request.getCookies();

			// If there is a cookie determine the user and show him his profile page
			if (ck != null) {
				String data = ck[0].getValue();
				if (!data.equals("") || data != null) {

					String data_string = new String(Base64.getDecoder().decode(data));
					// System.out.println(data_string);

					JSONParser parser = new JSONParser();
					JSONObject user_data_cookie = (JSONObject) parser.parse(data_string);

					System.out.println(user_data_cookie);
					String id = user_data_cookie.get("id").toString();
					String email = user_data_cookie.get("email").toString();
					String password = user_data_cookie.get("password").toString();

					Validation valid = new Validation();
					boolean emailValid = valid.mailCheck(email);
					boolean passwordValid = valid.validString(password, 30, false);

					if (emailValid == false || passwordValid == false) {
						System.out.println("Cookie contains corrupt data");
						RequestDispatcher rd = request.getRequestDispatcher("/index.html");
						rd.forward(request, response);
					}

					/*
					 * BuyerDTO buyer = new BuyerDTO();
					 * buyer.setEmail(email);
					 * buyer.setPassword(password);
					 * SellerDTO seller = new SellerDTO();
					 * seller.setEmail(email);
					 * seller.setPassword(password);
					 * BuyerDAOImpl buyerDAO = new BuyerDAOImpl();
					 * SellerDAOImpl sellerDAO = new SellerDAOImpl();
					 */
					UserDTO user = new UserDTO();
					user.setId(id);
					UserDAOImpl userDAO = new UserDAOImpl();
					userDAO.selectUser(user);
					String user_role = user.getUser_role().toString();

					// If the email and password are valid log the user in
					if (user_role.equals("Buyer")) {
						RequestDispatcher rd = request.getRequestDispatcher("/index_logged_in.html");
						response.addCookie(ck[0]);
						rd.forward(request, response);
					} else if (user_role.equals("Seller")) {
						RequestDispatcher rd = request.getRequestDispatcher("/index_logged_in.html");
						response.addCookie(ck[0]);
						rd.forward(request, response);
					} else {
						// Server the default page
						RequestDispatcher rd = request.getRequestDispatcher("/index.html");
						rd.forward(request, response);
					}
				}
			} else {
				// Server the default page
				RequestDispatcher rd = request.getRequestDispatcher("/index.html");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
