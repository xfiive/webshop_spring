<template>
    <header class="header">
        <div class="header__message">
            <div class="message__inner">Subscribe + Save 15%</div>
        </div>
        <div class="header__content">
            <div class="header__icons">
                <NuxtLink to="https://www.instagram.com/" class="icons__photo"><img src="/Header/instagram.png"
                                                                                    alt="instagram"></NuxtLink>
                <NuxtLink to="https://www.facebook.com/" class="icons__photo"><img src="/Header/facebook.png"
                                                                                   alt="facebook"></NuxtLink>
            </div>

            <div class="header__nav">
                <!--				<NuxtLink @click="toggleMenu" class="nav_link">menu</NuxtLink>-->
                <NuxtLink to="#" class="nav_link">bundles</NuxtLink>
                <NuxtLink to="/shop" class="nav_link">shop</NuxtLink>
                <NuxtLink to="/shop" class="nav_link">shop</NuxtLink>
                <NuxtLink to="/" class="nav_link header__logo">parjorie<br>creations</NuxtLink>
                <NuxtLink to="#" class="nav_link">abous us</NuxtLink>
                <NuxtLink to="/admin" class="nav_link">Admin</NuxtLink>
                <button class="nav_link"><img src="/Header/cart.png" alt="cart"></button>
            </div>

            <div class="header__icons">
                <!--				<NuxtLink class="icons__photo" @click="openCart()"><img src="/Header/cart.png" alt="cart"></NuxtLink>-->
            </div>
        </div>
    </header>

    <div class="header__menu">
        <div class="menu__wrapper">
            <NuxtLink to="/shop/#macaroons" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">macroons</div>
            </NuxtLink>
            <NuxtLink to="/shop/#cupcakes" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">cupcakes</div>
            </NuxtLink>
            <NuxtLink to="#" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">cookies</div>
            </NuxtLink>
        </div>

        <div class="menu__wrapper">
            <NuxtLink to="#" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">cake pops</div>
            </NuxtLink>
            <NuxtLink to="/shop/#cakes" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">cakes</div>
            </NuxtLink>
            <NuxtLink to="#" class="menu__item">
                <img class="item__photo" src="/Header/macroons.jpg" alt="macroons">
                <div class="item__name">pies</div>
            </NuxtLink>
        </div>
    </div>

    <div class="cart__wrapper">
        <div class="cart__menu">
            <!--			<a @click="closeCart()" class="delete-button">-->
            <!--				<img src="/Header/Close_cart.png" alt="" class="delete-photo">-->
            <!--			</a>-->
            <div class="cart__text">Your Order</div>
            <!--			<div class="products">
                            <div class="product__card" v-for="item in cart">
                                <img :src="item.productImage" alt="" class="product-photo">
                                <div class="product-name">
                                    {{ item.productName }}
                                </div>
                                <hr class="product-hr">
                                <div class="product-name">$ {{ item.amount * 10 }}.00</div>
                                <div class="product__amout-section">
                                    <div @click="subractNum(item)" class="product-quantity">-</div>
                                    <div class="product-amount">{{ item.amount }}</div>
                                    <div @click="addNum(item)" class="product-quantity">+</div>
                                </div>
                            </div>
                        </div>
                        <BaseButton @click="order(); closeCart()" class="order-button" />-->
        </div>
    </div>
</template>

<script setup lang="ts">



</script>

<!--

<script setup>
import { useMyProductCartStore } from '~/stores/productCart';

const products = ref({});

const cartStore = useMyProductCartStore();

const { cart } = storeToRefs(cartStore);

const toggleMenu = () => {
	document.querySelector(".header__menu").classList.toggle("menu__active");
};

const openCart = () => {
	document.body.style.overflow = "hidden";
	document.querySelector(".cart__wrapper").style.display = "block";
	document.querySelector(".cart__menu").classList.add("active_cart");
};

const closeCart = () => {
	document.body.style.overflow = "auto";
	document.querySelector(".cart__menu").classList.remove("active_cart");
	document.querySelector(".cart__wrapper").style.display = "none";
};

const order = () => {
	if (Object.keys(products).length == 0) return;
	alert("Thank you for your purchase!");
	localStorage.clear();
	window.location.reload();
};

const addNum = (item) => {
	item.amount++;
};

const subtractNum = (item) => {
	if (item.amount > 1) item.amount&#45;&#45;;
};
</script>
-->

<style scoped>
.header {
    position: fixed;
    top: 0;
    z-index: 2;
    width: 100%;
    background-color: #fff;
}

.header__message {
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    font-weight: 600;
    color: #FFFFFF;
    height: 40px;
    width: 100%;
    background-color: #EF3F75;
}

.header__content {
    display: flex;
    height: 100px;
    max-width: 1300px;
    margin: auto;
    padding: 0 20px;
    align-items: center;
    justify-content: space-between;
}

.header__icons {
    display: flex;
    height: 100%;
    width: 100px;
    align-items: center;
    justify-content: space-between;
}

.header__icons:last-child {
    justify-content: flex-end;
}

.icons__photo {
    display: block;
    padding: 5px;
}

.icons__photo img {
    height: 24px;
    width: 24px;
}

.header__nav {
    display: flex;
    flex: 1;
    justify-content: space-between;
    padding: 0 40px;
    align-items: center;
}

.nav_link {
    display: block;
    text-decoration: none;
    font-size: 14px;
    color: #EF3F75;
    font-weight: 500;
}

.header__logo {
    text-align: center;
    font-size: 24px;
    font-weight: 700;
}

.header__menu {
    z-index: 1;
    position: fixed;
    top: 140px;
    display: flex;
    width: 100%;
    height: 300px;
    background-color: #FFFFFF;
    justify-content: space-between;
    border-radius: 0 0 35px 35px;
    box-shadow: 0 3px 8px rgba(117, 117, 117, 0.521);
    transform: translateY(-310px);
    transition: .2s;
}

.menu__active {
    transform: translateY(0px);
}

.menu__wrapper {
    display: flex;
}

.menu__item {
    display: block;
    text-decoration: none;
    height: inherit;
    width: 200px;
    padding-top: 20px;
    margin: 0 40px;
}

.item__photo {
    display: block;
    width: 200px;
    height: 200px;
    background-color: teal;
}

.item__name {
    margin-top: 20px;
    font-size: 20px;
    font-weight: 700;
    color: #706767;
    text-align: center;
}

.cart__wrapper {
    display: none;
    top: 0;
    z-index: 4;
    position: fixed;
    height: 100dvh;
    width: 100%;
    background-color: #8f8f8f52;

}

.cart__menu {
    position: relative;
    /* transition: 10s all ease; */
    transform: translateX(600px);
    top: 0;
    right: 0;
    z-index: 3;
    width: 600px;
    height: 100vh;
    position: fixed;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.active_cart {
    transform: translateX(0px);
}

.delete-button {
    display: block;
    padding: 15px;
}

.cart__text {
    width: 100%;
    text-align: center;
    font-size: 40px;
    font-weight: 700;
    margin: 10px;
}

.products {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    flex: 1;
    overflow-y: scroll;
}

.product__card {
    box-sizing: content-box;
    display: flex;
    flex-direction: column;
    width: 200px;
    height: 300px;
    align-items: center;
    margin: 10px;
    border: 2px solid black;
    border-radius: 30px;
    padding: 10px;
}

.product-photo {
    display: block;
    width: 190px;
    height: 150px;
}

.product-name {
    margin-top: 10px;
}

.product-hr {
    margin-top: 10px;
    width: 50%;
}

.product__amout-section {
    display: flex;
    justify-content: center;
    font-size: 20px;
    margin-top: 10px;
    font-weight: 700;
}

.product-amount {
    color: #ff9091;
}

.product-quantity {
    cursor: pointer;
    padding: 0 10px;
}

.order-button {
    margin: 15px;
    align-self: center;
}

a {
    cursor: pointer;
}
</style>
<script setup lang="ts">
</script>