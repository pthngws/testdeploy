// Toggle để hiển thị/ẩn form thêm ảnh
//function toggleImageForm() {
//    const imageForm = document.getElementById('image-form');
//   imageForm.style.display = imageForm.style.display === 'none' ? 'block' : 'none';
//}

// Thêm trường input cho tên ảnh và URI của ảnh
function addImageInput() {
	const container = document.getElementById('image-uri-container');
	const inputField = document.createElement('div');
	inputField.classList.add('form-group');
	inputField.innerHTML = `
	<label for="imageName">Image Name:</label> <input type="text"
								name="imageName[]" placeholder="Enter image name"
								class="form-control"> <label for="imageURI">Image
								URI:</label> <input type="text" name="imageURI[]"
								placeholder="Enter image URI" class="form-control"
								oninput="showImagePreview(this)">

							<!-- Thẻ img để hiển thị ảnh khi người dùng nhập URL -->
							<img id="image-preview" src="" alt="Image Preview"
								style="max-width: 300px; margin-top: 10px; display: none;">
    `;
	container.appendChild(inputField);
}

function saveImages() {
	// Validate the inputs (optional)
	const imageInputs = document.querySelectorAll('input[name="imageName[]"], input[name="imageURI[]"]');
	let allFieldsFilled = true;
	imageInputs.forEach(input => {
		if (!input.value.trim()) {
			allFieldsFilled = false;
		}
	});

	// If validation fails, show an alert (optional)
	if (!allFieldsFilled) {
		alert("Please fill in all image fields before saving.");
		return;
	}

	// Show success message
	const successMessage = document.getElementById("image-success-message");
	successMessage.style.display = "block";

	// Optionally, hide the message after a few seconds
	setTimeout(() => {
		successMessage.style.display = "none";
	}, 3000);
}

function saveImages() {
	alert("Save Images button clicked!");
}

function showImagePreview(input) {
       var imageUrl = input.value; // Lấy URL từ input
       var imgElement = input.closest('.form-group').querySelector('#image-preview');

       if (imageUrl) {
           imgElement.style.display = 'block'; // Hiển thị thẻ img
           imgElement.src = imageUrl; // Cập nhật nguồn ảnh
       } else {
           imgElement.style.display = 'none'; // Ẩn thẻ img nếu không có URL
       }
   }
