function togglePasswordVisibility(inputId, eyeIconId) {
    var inputField = document.getElementById(inputId);
    var eyeIcon = document.getElementById(eyeIconId);
    if (inputField.type === "password") {
        inputField.type = "text";
        eyeIcon.innerHTML = '<i class="fa fa-eye-slash"></i>'; // Mắt nhắm
    } else {
        inputField.type = "password";
        eyeIcon.innerHTML = '<i class="fa fa-eye"></i>'; // Mắt mở
    }
}
