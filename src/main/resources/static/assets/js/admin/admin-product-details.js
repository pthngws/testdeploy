// JavaScript cho các hiệu ứng động nếu cần, ví dụ như validation hoặc hover effect
document.addEventListener('DOMContentLoaded', function () {
    // Code cho các hiệu ứng hoặc tính năng bổ sung, ví dụ:
    const alert = document.querySelector('.alert');
    if (alert) {
        setTimeout(() => {
            alert.style.display = 'none';
        }, 5000); // Ẩn thông báo sau 5 giây
    }
});
