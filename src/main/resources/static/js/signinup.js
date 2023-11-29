document.getElementById("signinContanier").style.display = "block"
document.getElementById("signupContanier").style.display = "none"
const changeForm = function() {
	const signin = document.getElementById("signinContanier")
	const signup = document.getElementById("signupContanier")

	if (signin.style.display == "block") {
		signin.style.display = "none";
		signup.style.display = "block";
	}
	else {
		signin.style.display = "block";
		signup.style.display = "none";
	}

}