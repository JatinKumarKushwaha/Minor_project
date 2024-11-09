// Use new api to check if the user is buyer or seller
// https://javascript.info/formdata
// https://developer.mozilla.org/en-US/docs/Web/API/FormData/FormData
// https://developer.mozilla.org/en-US/docs/Learn/Forms/Sending_forms_through_JavaScript#using_xmlhttprequest_and_the_formdata_object

const api_url = "http://localhost:8080/Agrify/api/user/check";

const form = document.getElementById("loginform");

function loginCheck() {
	// Get data from the form and then call the user_check api to check if the user is a buyer or seller
	// Redirect the user to there profile page after that

	const XHR = new XMLHttpRequest();

	const formData = new FormData(form);

	let formDataJSON = {};

	for (const [key, value] of formData) {
		formDataJSON[key] = value;
	}

	XHR.addEventListener("load", (event) => {
		console.log(event.target.responseText);
	});

	XHR.addEventListener("error", () => {
		console.log("Oops! Something went wrong.");
	});

	XHR.open("POST", "http://localhost:8080/Agrify/login");

	console.log(formDataJSON);

	fetch(api_url, {
		method: "POST",
		body: JSON.stringify(formDataJSON),
		headers: {
			"Content-type": "application/json; charset=UTF-8"
		}
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error("NETWORK RESPONSE ERROR");
			}
		}).then(data => {
			console.log(data);
			if (data.password == "invalid") {
				// Tell user that the password is too long
				console.log("password is too long");
			}
			if (data.email == "invalid") {
				// Tell user that the email is too long or is incorrect
				console.log("email is too long or incorrect");
			}
			if (data.password != "not-matching" && data.email != "not-registered") {
				if (data.role == "Buyer") {
					XHR.send(formData);
				} else if (data.role == "Seller") {
					XHR.send(formData);
				} else {
					console.log("Something went wrong");
				}
			} else if (data.email == "not-registered") {
				form.querySelector("#login-email");
			}
		})
		.catch((error) => console.error("FETCH ERROR:", error));
}


form.addEventListener("submit", (e) => {
	e.preventDefault();
	loginCheck();
});


function loginToggle() {
	document.querySelector(".login").classList.toggle("activelogin");
	document.querySelector(".images").classList.toggle("container3blur");
	document.querySelector(".container2").classList.toggle("container3blur");
	document.querySelector(".container1").classList.toggle("container3blur");
	document.querySelector(".container3").classList.toggle("container3blur");
	document.querySelector(".container4").classList.toggle("container3blur");
	document.querySelector(".container5").classList.toggle("container3blur");
	document.querySelector(".container6").classList.toggle("container3blur");

}

function myFunction(x) {
	x.classList.toggle("change");
	var y = document.querySelector(".sidebar");

	// y.classList.toggle("show")
	toggleStyle();
	function toggleStyle() {
		if (y.style["transform"] == "scaleX(1)")
			y.style.transform = "scaleX(0)";
		else
			y.style.transform = "scaleX(1)";
	}
}

