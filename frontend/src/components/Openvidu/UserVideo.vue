<template>
<div v-if="streamManager">
    <ov-video :stream-manager="streamManager"/>
    <div class="inactive-user" v-if="ss">
        <div class="inactive-user-info" v-if="!streamManager.stream.videoActive">
            <div class="inactive-user-name"><p>{{clientData}}</p></div>
        </div>
        <div class="inactive-user-video" v-if="!streamManager.stream.videoActive">
            <div class="inactive fas fa-video-slash"></div>
        </div>
        <div class="inactive-user-audio" v-if="!streamManager.stream.audioActive">
            <div class="inactive fas fa-microphone"></div>
        </div>
    </div>
</div>
</template>

<script>
import OvVideo from './OvVideo';
export default {
	name: 'UserVideo',
	components: {
		OvVideo,
	},
	props: {
		streamManager: Object,
        ss:Boolean
	},
	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},
	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>

<style scoped>
.active-user {
    width: 100%;
    height: auto;
    border-radius : 20px;
    vertical-align: middle;
}
.inactive-user{
    position: absolute;
    width: 100%;
    height: 100%;
    bottom: 0px;
}
.inactive-user-img{
    width : 30%;
    border-radius: 50%;
    background-color: none;
    display: block;
    margin: auto;
    margin-top : 10%;
    margin-bottom: 5%;
}
.inactive-user-name{
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-regular);
    font-size: var(--font-size-30);
    color : #007700;
    text-align: center;
}
.inactive-user-video{
    display: inline-block;
    position: absolute;
    right: 5%;
    bottom: 5%;
}
.inactive-user-audio{
    display: inline-block;
    position: absolute;
    right: 15%;
    bottom: 5%;
}
.inactive-user .inactive{
    font-size : 15px!important;
    color : var(--color-red)!important;
}

.screen-video video{
    border-radius: 0px;
    max-height: 600px;
}
.screen-video .active-user-name{
    display: none;
}
</style>