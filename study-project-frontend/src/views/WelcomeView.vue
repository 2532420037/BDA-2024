<template>
  <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex; justify-content: center; align-items: center; position: relative;">
    <!-- 背景图片 -->
    <div style="position: absolute; width: 100%; height: 100%; z-index: -1;">
      <el-image style="width: 100%; height: 100%" fit="cover"
                src="https://q0.itc.cn/q_70/images01/20240422/d519b4424ad44d7eaf0ddd3a4e08448e.jpeg"/>
    </div>

    <!-- 系统主题与描述，置于顶部中央 -->
    <div class="welcome-title">
      <div style="font-size: 35px; font-weight: bold; text-align: center;">欢迎使用 Linked Papers</div>
      <div style="margin-top: 10px; text-align: center; font-size: 16px;">本平台汇总了来自arXiv计算机学科40个子领域的169,343篇论文</div>
    </div>

    <!-- 登录框 -->
    <div style="width: 400px; background-color: rgba(255, 255, 255, 0.8); padding: 20px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.welcome-title {
  position: absolute;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  text-shadow: 0 0 10px black;
  max-width: 80%;
  text-align: center;
}
</style>
