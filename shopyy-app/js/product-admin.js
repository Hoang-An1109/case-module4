
$(document).ready(function(){
  productHome();
  loadColors();
  loadSizes();
  loadSubCategories();
  loadColor();
  loadSize();
  loadSubCategorie();
});

function loadColors() {
  $.ajax({
    url: `http://localhost:8080/api/colors`, 
    type: "GET",
    crossDomain: true,
    contentType: "application/json",
    dataType: "json",
    success: function (response) {
      localStorage.setItem("colors", JSON.stringify(response));
      response?.forEach((color) => {
        select.append(new Option(color.colorName, color.id));
      });
    },
    error: (e) => {
      console.log(e);
    },
  });
}

function loadSizes() {
  $.ajax({
    url: `http://localhost:8080/api/sizes`, 
    type: "GET",
    crossDomain: true,
    contentType: "application/json",
    dataType: "json",
    success: function (response) {
      localStorage.setItem("sizes", JSON.stringify(response));
      response?.forEach((size) => {
        select.append(new Option(size.sizeName, size.id));
      });
    },
    error: (e) => {
      console.log(e);
    },
  });
}

function loadSubCategories() {
  $.ajax({
    url: `http://localhost:8080/api/subCategory`, 
    type: "GET",
    crossDomain: true,
    contentType: "application/json",
    dataType: "json",
    success: function (response) {
      localStorage.setItem("subCategories", JSON.stringify(response?.content));
      response?.content?.forEach((subCategory) => {
        select.append(new Option(subCategory.name, subCategory.id));
      });
    },
    error: (e) => {
      console.log(e);
    },
  });
}

async function successHandler() {
  try {
    const data = await $.ajax({
      type: "GET",
      url: "http://localhost:8080/api/products",
    });
    productHome();
  } catch (error) {
    console.error("Error while fetching products:", error);
  }
}

let currentPage = 0;
const pageSize = 5;

function productHome(search = "") {
  $.ajax({
      type: "GET",
      url: `http://localhost:8080/api/products?page=${currentPage}&size=${pageSize}&search=${search}`,
      success: function (data) {
          let content = "";
          data.content.forEach((product) => {
              content += getProduct(product);
          });

          // Thêm nội dung mới vào danh sách hiện tại
          $("#display-list").append(content);

          // Kiểm tra xem có cần ẩn nút "Xem Thêm" hay không
          if (currentPage >= data.totalPages - 1) {
              $("#load-more").hide();
          } else {
              $("#load-more").show();
          }

          currentPage++;
          // attachEventListenersProductList();
      },
      error: function (xhr, status, error) {
          console.error("Error while fetching products:", error);
      }
  });
}

function getProduct(product) {
    const colors = product.colors && product.colors.length > 0 ? product.colors.map(color => color.colorName).join(", ") : "";
    const sizes = product.sizes && product.sizes.length > 0 ? product.sizes.map(size => size.sizeName).join(", ") : "";
    const subCategory = product.subCategory ? product.subCategory.name : "";

    return `<tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.description}</td>
                <td>${product.quantity}</td>
                <td><img src="${product.img}" width="100"></td>
                <td>${colors}</td>
                <td>${sizes}</td>
                <td>${subCategory}</td>
                <td><button class="btn btn-success my-3 " onclick="deleteProduct(${product.id})">Delete</button></td>
                <td><button class="btn btn-success my-3 " type="button" data-toggle="modal" data-target="#exampleModalLong" onclick='showFormUpdate(${product.id})'>Update</button></td>
            </tr>`;
}

function deleteProduct(id) {
  $.ajax({
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    type: "DELETE",
    url: `http://localhost:8080/api/products/${id}`,
    success: function () {
      $(`#product-${id}`).remove();
      location.reload();
      alert("The product has been successfully deleted");
    },
  });
}

function handleSearch() {
  const query = $('#search-input').val();
  $.ajax({
      headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
      },
      type: "GET",
      url: `http://localhost:8080/api/products/search?keyword=${query}`,
      success: function (data) {
          const table = $('#display-list');
          table.find("tr:gt(0)").remove();
          data.content.forEach(product => {

              const colors = product.colors && product.colors.length > 0 ? product.colors.map(color => color.colorName).join(", ") : "";
              const sizes = product.sizes && product.sizes.length > 0 ? product.sizes.map(size => size.sizeName).join(", ") : "";
              const subCategory = product.subCategory ? product.subCategory.name : "";

              const row = `<tr>
                  <td>${product.name}</td>
                  <td>${product.price}</td>
                  <td>${product.description}</td>
                  <td>${product.quantity}</td>
                  <td><img src="${product.image}" alt="Product Image" style="width: 50px; height: 50px;"></td>
                  <td>${colors}</td>
                  <td>${sizes}</td>
                  <td>${subCategory}</td>
                  <td><button onclick="deleteProduct(${product.id})">Delete</button></td>
                  <td><button onclick="updateProduct(${product.id})">Update</button></td>
              </tr>`;
              table.append(row);
          });
      },
      error: function (xhr, status, error) {
          console.error(xhr.responseText);
      },
  });
}


