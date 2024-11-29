// Toggle để hiển thị/ẩn form thêm ảnh
function toggleImageForm() {
    const imageForm = document.getElementById('image-form');
    imageForm.style.display = imageForm.style.display === 'none' ? 'block' : 'none';
}

// Thêm trường input cho tên ảnh và URI của ảnh
function addImageInput() {
    const container = document.getElementById('image-uri-container');
    const inputField = document.createElement('div');
    inputField.classList.add('form-group');
    inputField.innerHTML = `
        <label for="imageName">Image Name:</label>
        <input type="text" name="imageName[]" placeholder="Enter image name" class="form-control">
        <label for="imageURI">Image URI:</label>
        <input type="text" name="imageURI[]" placeholder="Enter image URI" class="form-control">
    `;
    container.appendChild(inputField);
}