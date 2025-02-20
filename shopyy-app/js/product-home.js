$(document).ready(function () {
    productHome();
  });
  
  
  
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
      },
      error: function (xhr, status, error) {
        console.error("Error while fetching products:", error);
      },
    });
  }
  
  function getProduct(product) {
    return `<tr>
                  <td >${product.name}</td>
                  <td>${product.price}</td>
                  <td class="d-flex col-5 justify-content-center btn btn-outline-primary"><img src="${product.img}" width="100"></td>
                  <td>
                    <div class="quantity-control">
                        <button class="decrease-quantity" data-product-id="${product.id}">-</button>
                        <input type="number" class="quantity-input" value="1" min="1" max="${product.quantity}" data-product-id="${product.id}">
                        <button class="increase-quantity" data-product-id="${product.id}">+</button>
                    </div>
                  </td>
                  <td>
                      <button class="add-to-cart" data-product-id="${product.id}">Add to Cart</button>
                  </td>
                  <td><button class="d-flex col-5 align-items-center btn btn-outline-secondary" onclick="getProductDetail(${product.id})">SEE DETAILS</button></td>
              </tr>`;
  }
  

  function createProductRow(product) {
    return `<tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td class="d-flex col-5 justify-content-center"><img src="${product.img}" width="100"></td>
                <td>
                  <div class="quantity-control">
                      <button class="decrease-quantity" data-product-id="${product.id}">-</button>
                      <input type="number" class="quantity-input" value="1" min="1" max="${product.quantity}" data-product-id="${product.id}">
                      <button class="increase-quantity" data-product-id="${product.id}">+</button>
                  </div>
                </td>
                <td>
                    <button class="add-to-cart" data-product-id="${product.id}">Add to Cart</button>
                </td>
                <td><button class="d-flex col-5 align-items-center btn btn-outline-secondary" onclick="getProductDetail(${product.id})">SEE DETAILS</button></td>
            </tr>`;
}
  function handleSearch() {
    const query = $("#search-input").val();
    $.ajax({
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      type: "GET",
      url: `http://localhost:8080/api/products/search?keyword=${query}`,
      success: function (data) {
        const table = $("#display-list");
        table.find("tr:gt(0)").remove();
        data.content.forEach((product) => {
          const row = `<tr>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td class="d-flex col-5 justify-content-center btn btn-outline-primary"><img src="${product.image}" alt="Product Image" style="width: 50px; height: 50px;"></td>
                    <td>
                    <div class="quantity-control">
                        <button class="decrease-quantity" data-product-id="${product.id}">-</button>
                        <input type="number" class="quantity-input" value="1" min="1" max="${product.quantity}" data-product-id="${product.id}">
                        <button class="increase-quantity" data-product-id="${product.id}">+</button>
                    </div>
                    </td>
                    <td>
                        <button class="add-to-cart" data-product-id="${product.id}">Add to Cart</button>
                    </td>
                    <td><button class="d-flex col-5 align-items-center btn btn-outline-secondary" onclick="getProductDetail(${product.id})">SEE DETAILS</button></td>
                </tr>`;
          table.append(row);
        });
      },
      error: function (xhr, status, error) {
        console.error(xhr.responseText);
      },
    });
  }
  

function arrangePrice(order) {
    console.log(order);
    $.ajax({
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        method: "GET",
        url: `http://localhost:8080/api/products/price?value=${order}`,
        success: function (data) {
          const table = $("#display-list");
          table.find("tr:gt(0)").remove();
          data.content.forEach((product) => {
            const row = `<tr>
                      <td>${product.name}</td>
                      <td>${product.price}</td>
                      <td class="d-flex col-5 justify-content-center btn btn-outline-primary"><img src="${product.image}" alt="Product Image" style="width: 50px; height: 50px;"></td>
                      <td>
                        <div class="quantity-control">
                            <button class="decrease-quantity" data-product-id="${product.id}">-</button>
                            <input type="number" class="quantity-input" value="1" min="1" max="${product.quantity}" data-product-id="${product.id}">
                            <button class="increase-quantity" data-product-id="${product.id}">+</button>
                        </div>
                      </td>
                      <td>
                          <button class="add-to-cart" data-product-id="${product.id}">Add to Cart</button>
                      </td>
                      <td><button class="d-flex col-5 align-items-center btn btn-outline-secondary" onclick="getProductDetail(${product.id})">SEE DETAILS</button></td>
                  </tr>`;
            table.append(row);
          });
        },
        error: function (error) {
            console.error('Error fetching products:', error);
        }
    });
}

function getProductDetail(id) {
  localStorage.setItem("productId", id);
  window.location.href = "http://localhost:63343/shopyy-app/html/product-detail.html";
}

function goToCart() {
  window.location.href = "http://localhost:63343/shopyy-app/html/cart.html";
}

function attachEventListenersProductList() {
  $(document).on('click', '.decrease-quantity', decreaseQuantityProductList);
  $(document).on('click', '.increase-quantity', increaseQuantityProductList);
  $(document).on('change', '.quantity-input', validateQuantityProductList);
  $(document).on('click', '.add-to-cart', addToCart);
}

function decreaseQuantityProductList() {
  let input = $(this).siblings('.quantity-input');
  let currentValue = parseInt(input.val());
  if (currentValue > 1) {
      input.val(currentValue - 1);
  }
}

function increaseQuantityProductList() {
  let input = $(this).siblings('.quantity-input');
  let currentValue = parseInt(input.val());
  let maxValue = parseInt(input.attr('max'));
  if (currentValue < maxValue) {
      input.val(currentValue + 1);
  }
}

function validateQuantityProductList() {
  let value = parseInt($(this).val());
  let min = parseInt($(this).attr('min'));
  let max = parseInt($(this).attr('max'));
  if (value < min) {
      $(this).val(min);
  } else if (value > max) {
      $(this).val(max);
  }
}

function addToCart() {
  let productId = $(this).data('product-id');
  let quantity = parseInt($(this).closest('tr').find('.quantity-input').val())
  let stockQuantityElement = $(this).closest('tr').find('.stock-quantity')
  let currentStockQuantity = parseInt(stockQuantityElement.text())

  $.ajax({
      url: 'http://localhost:8080/api/cart/add',
      method: 'POST',
      contentType: 'application/json',
      headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
      },
      data: JSON.stringify({
          productId: productId,
          quantity: quantity
      }),
      success: function (response) {
          alert('Product added to cart successfully');
          let newStockQuantity = currentStockQuantity - quantity
          stockQuantityElement.text(newStockQuantity)

          let quantityInput = $(`input.quantity-input[data-product-id="${productId}"]`)
          quantityInput.attr('max', newStockQuantity)

          if (parseInt(quantityInput.val()) > newStockQuantity) {
              quantityInput.val(newStockQuantity)
          }
      },
      error: function (xhr, status, error) {
          if (xhr.status === 401) {
              alert('Phiên đăng nhập của bạn đã hết hạn. Vui lòng đăng nhập lại');
              window.location.href = 'login.html';
          } else {
              alert('Error adding product to cart: ' + xhr.responseText);
          }
      }
  });
}

$(document).ready(function() {
  attachEventListenersProductList();
});