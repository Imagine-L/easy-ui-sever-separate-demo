<template>
  <router-view></router-view>
</template>

<!--<script setup>-->
<!--import {onMounted} from "vue";-->
<!--import {useRouter} from 'vue-router'-->
<!--import storage from "@/utils/storage";-->
<!--import {session} from "@/constant";-->
<!--// import {toMain} from "@/api/user";-->

<!--const router = useRouter();-->

<!--// const getPageName = () => {-->
<!--//   return new Promise((resolve, reject) => {-->
<!--//     toMain().then(resp => {-->
<!--//       console.log(resp.status)-->
<!--//       if (resp.status >= 200 && resp.status < 300) {-->
<!--//         resolve('main')-->
<!--//       } else {-->
<!--//         resolve('login')-->
<!--//       }-->
<!--//     }).catch(error => {-->
<!--//       reject(error)-->
<!--//     })-->
<!--//   })-->
<!--// }-->

<!--onMounted(() => {-->
<!--  // if (storage.sessionKeyIsEmpty(session.SESSION_USER_TAG)) {-->
<!--  //   getPageName().then(pageName => {-->
<!--  //     console.log(pageName)-->
<!--  //     router.push({name: pageName})-->
<!--  //   }).catch(() => {-->
<!--  //     router.push({name: 'login'})-->
<!--  //   })-->
<!--  // } else {-->
<!--  //   router.push({name: 'login'})-->
<!--  // }-->
<!--  router.push({name: 'login'})-->
<!--})-->

<!--</script>-->

<style>

</style>
