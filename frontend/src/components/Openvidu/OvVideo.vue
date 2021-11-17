<template>
	<video class="active-user" autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',
	props: {
		streamManager: Object,
	},
	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
.active-user {
    width: 100%;
    height: 100%;
    border-radius : 20px;
    vertical-align: middle;
		object-fit: fill;
}
</style>