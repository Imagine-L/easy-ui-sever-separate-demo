<template>
  <el-container>

    <el-aside width="200px">
      <el-row>
        <el-col :span="24">
          <el-image
              style="
                width: 120px;
                height: 60px;
                display: block;
                margin: 40px auto;
              "
              fit="fill"
              :src="require('@/assets/image/main_logo.svg')"
          ></el-image>
          <el-menu
              active-text-color="#ffd04b"
              background-color="#191A22"
              class="el-menu-vertical-demo"
              default-active="1-1"
              text-color="#fff"
          >
            <el-sub-menu index="1">
              <template #title>
                <el-icon></el-icon>
                <span>系统管理</span>
              </template>

              <el-menu-item index="1-1" @click="open('user')">
                用户管理
              </el-menu-item
              >
              <el-menu-item index="1-2" @click="open('role')"> <!--v-hasRole="['sys']"-->
                角色管理
              </el-menu-item
              >
            </el-sub-menu>
            <el-menu-item index="2">
              <el-icon></el-icon>
              <span>客户管理</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-aside>
    <el-container>
      <el-header>
        <span>昵称:&nbsp;{{ nickName }}</span>
        <el-button type="primary" @click="doLogout">登出</el-button>
      </el-header>
      <!--进行路由跳转-->
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import {actions} from "@/constant";
import {getUserInfo} from "@/api/user";

export default {
  name: 'mainPage',
  data() {
    return {
      // nickName: this.$store.state.user.nickName
    }
  },
  computed: {
    nickName() {
      return this.$store.state.user.nickName
    }
  },
  methods: {
    doLogout() {
      this.$store.dispatch(actions.LOGOUT).then(resp => {
        if (resp.status === 200) {
          this.$router.push({name: 'login'})
        }
      }).catch(error => {
        this.$store.dispatch(actions.EMPTY)
        // store.state.user.commit(mutations.EMPTY)
        this.$router.push({name: 'login'})
      })
    },
    open(name) {
      this.$router.push({name})
    }
  },
  mounted() {
    this.$router.push({name: 'user'})
  }
}

</script>

<style scoped>
.el-container {
  height: 100%;
}

.el-header {
  margin-bottom: 20px;
  background: #e9eef3;
}

.el-footer {
  line-height: 60px;
}

.el-aside {
  background-color: #191a22;
  color: var(--el-text-color-primary);
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #fcfcfc;
  color: var(--el-text-color-primary);
  /*text-align: center;*/
}
</style>