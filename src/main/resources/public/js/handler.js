handler = (function(){
    const login = (username, password) => {
        const user = {
            "username" : username,
            "password" : password
        }
        fetch("/login", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(user)
        }).then(res => {
            if(res.ok){
                console.log("It works!");
            } else{
                swal({
                    icon: "error",
                    title: "Error",
                    text: "Something went wrong."
                });
            }
        }).catch(() => alert("Something went wrong"));
    }

    return{
        login:login
    }
})();