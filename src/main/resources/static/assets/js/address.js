// Đường dẫn đến tệp JSON
const addressFilePath = "./assets/data/Address.json";

// Hàm tải dữ liệu từ tệp JSON
async function fetchAddressData() {
    try {
        const response = await fetch(addressFilePath);
        if (!response.ok) {
            throw new Error("HTTP error! Status: ${response.status}");
        }
        const data = await response.json();
        populateCities(data);
    } catch (error) {
        console.error("Error fetching address data:", error);
    }
}

// Hàm hiển thị danh sách tỉnh/thành phố
function populateCities(data) {
    const citySelect = document.getElementById("city");
    for (const city in data) {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        citySelect.appendChild(option);
    }
    // Lưu dữ liệu vào một biến toàn cục để sử dụng cho danh sách quận/huyện
    window.addressData = data;
}

// Hàm cập nhật danh sách quận/huyện dựa trên tỉnh/thành phố được chọn
function updateDistricts() {
    const citySelect = document.getElementById("city");
    const districtSelect = document.getElementById("district");

    // Xóa các quận/huyện cũ
    districtSelect.innerHTML = '<option value="">Chọn Quận/Huyện</option>';

    const selectedCity = citySelect.value;
    if (selectedCity && window.addressData[selectedCity]) {
        window.addressData[selectedCity].forEach(district => {
            const option = document.createElement("option");
            option.value = district;
            option.textContent = district;
            districtSelect.appendChild(option);
        });
    }
}

// Gọi hàm fetch dữ liệu khi tải trang
document.addEventListener("DOMContentLoaded", fetchAddressData);
