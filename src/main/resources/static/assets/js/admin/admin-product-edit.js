// Nếu cần thêm bất kỳ JavaScript nào, có thể bắt đầu từ đây

document.addEventListener("DOMContentLoaded", function() {
    // Ví dụ, có thể tự động cuộn đến phần đầu trang khi có thông báo lỗi
    if (document.querySelector('.alert-danger')) {
        window.scrollTo(0, 0); // Cuộn lên đầu trang
    }
});
