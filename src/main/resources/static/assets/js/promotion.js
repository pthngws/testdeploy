function showAddForm() {
    document.getElementById("add-promotion-form").style.display = "block";
    document.getElementById("promotions-list").style.display = "none";
}

function showPromotionList() {
    document.getElementById("add-promotion-form").style.display = "none";
    document.getElementById("promotions-list").style.display = "block";
}
document.addEventListener("DOMContentLoaded", function () {
    const successMessage = document.getElementById("successMessage");
    const errorMessage = document.getElementById("errorMessage");

    if (successMessage) {
        setTimeout(() => successMessage.style.display = "none", 5000); // 5 giây
    }
    if (errorMessage) {
        setTimeout(() => errorMessage.style.display = "none", 5000); // 5 giây
    }
});
document.addEventListener("DOMContentLoaded", function () {
    const errorFields = document.querySelectorAll("span[th\\:if]");
    if (errorFields.length > 0) {
        // Di chuyển đến phần tử lỗi đầu tiên
        errorFields[0].scrollIntoView({ behavior: "smooth", block: "center" });

        // Đặt con trỏ chuột vào input đầu tiên có lỗi
        const inputWithError = errorFields[0].previousElementSibling;
        if (inputWithError && inputWithError.tagName === "INPUT") {
            inputWithError.focus();
        }
    }
});