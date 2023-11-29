const showProfile = document.getElementById("showProfile")
        const showInfo = document.getElementById("showInfo")
        const showImage = document.getElementById("showImage")


        const toEditInfo = function(){
            
            showProfile.style.display = "none"
            showInfo.style.display = "block"

        }

        const toEditImage = function(){
            showProfile.style.display = "none"
            showImage.style.display = "block"
        }

        const toProfile = function(){
            showProfile.style.display = "block"
            showInfo.style.display = "none"
            showImage.style.display = "none"
        }