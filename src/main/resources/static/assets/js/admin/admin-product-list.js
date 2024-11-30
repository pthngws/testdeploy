// admin-product-list.js
document.addEventListener("DOMContentLoaded", function() {
    const deleteLinks = document.querySelectorAll('a[onclick*="return confirm"]');
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            const confirmDelete = confirm("Bạn chắc chắn muốn xóa sản phẩm này?");
            if (!confirmDelete) {
                event.preventDefault();
            }
        });
    });
});