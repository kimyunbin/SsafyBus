using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using Photon;
using UnityEngine.SceneManagement;


public class Launcher : Photon.PunBehaviour {

    public Text StatusText;
    public GameObject EmptyRoom;
    public GameObject Room;
    public Text RoomInfoText;

    void Update() => StatusText.text = PhotonNetwork.networkingPeer.State.ToString(); //현재 어떤 상태인지
    // void Update() => RoomInfoText.text = PhotonNetwork.room.ToString();
    // public enum ActivePanel
    // {
    //     ROOM = 1
    // }
    // public byte maxPlayer = 10;
    // public GameObject[] panels;
    // public GameObject[] room;
    // public GameObject[] gridTr;
    void Start()
    {
        Debug.Log("마스터 서버 연결 완료");
        // PhotonNetwork.GameVersion = gameVersion; // 게임 버전 맞추기
        PhotonNetwork.ConnectUsingSettings("0.1"); // 내가 설정한 포톤서버 마스터 서버 연결
    }

    // 마스터 서버에 접속 성공했을 때 
    public override void OnConnectedToMaster()
    {
        Debug.Log("마스터 서버로 접속 완료");
        // connectionInfoText.text = "Online: Connected to the Master Server";
        PhotonNetwork.playerName = "닉네임 임의로 설정";
        PhotonNetwork.JoinLobby();
    }

    // 로비까지 들어 왔으면 
    public override void OnJoinedLobby()
    {
        Debug.Log("로비 들어옴");
    }

    public void SetPanel(){

        if (PhotonNetwork.room != null){
            Room.SetActive(true);
        }
        else {
            EmptyRoom.SetActive(true);
        }
        
    }

    // 방만들기 ------------------------------------------
    // public void CreateRoom() => PhotonNetwork.CreateRoom("광주", new RoomOptions { MaxPlayers = 10 });
    public void CreateRoom(){
        PhotonNetwork.CreateRoom("광주" );
        EmptyRoom.SetActive(false);
        Room.SetActive(true);
    }
    
    public override void OnCreatedRoom() {

        Debug.Log("방 만들기 완료");
        Debug.Log(PhotonNetwork.room);
        RoomInfoText.text = PhotonNetwork.room.ToString();
        // ChangePanel(ActivePanel.ROOOM);
    }


    //임의로 씬 변경
    public void ChangeScene(){
        SceneManager.LoadScene("SampleScene");
        
    }

    // 방입장--------------------------------------------------------------------
    // public void JoinRoom(){
    //     // PhotonNetwork.JoinRoom("광주");}
    // }
    // public override void OnJoinedRoom() {
    //     // Debug.Log("방 참가 완료");
    //     // SceneManager.LoadScene("SampleScene");
    // }




    // 방만들고입장-------------------------------------------------------------------------------
    public void JoinOrCreateRoom(){
        // PhotonNetwork.JoinOrCreateRoom("광주", new RoomOptions { MaxPlayers = 15 }, null);
        // SceneManager.LoadScene("SampleScene");
        Debug.Log(PhotonNetwork.room);
        // RoomInfoText.text = PhotonNetwork.room.ToString();
    }

    // public override void OnJoinOrCreateRoom() {
    //     // RoomRenewal();
        
    


// -------------------------------------------------------------------------------
    public void JoinRandomRoom() => PhotonNetwork.JoinRandomRoom();

    public void LeaveRoom() => PhotonNetwork.LeaveRoom();


    // public void InRoom()
    // {
    //     SceneManager.LoadScene("SampleScene");
    // }


 

    // public override void OnCreateRoomFailed(){

    //     Debug.Log("방만들기실패");
    // }

    // public override void OnJoinRoomFailed()
    // {

    //     Debug.Log("방참가실패");
    // }

    // public override void OnJoinRandomFailed()
    // {

    //     Debug.Log("방랜덤참가실패");
    // }

    // public void titleopen()
    // {
    //     MenuManager.Instance.OpenMenu("room");//로비에 들어오면 타이틀 메뉴 키기
    // }


    // // 연결 끊기
    // public void Disconnect() => PhotonNetwork.Disconnect();

    // public override void OnDisconnected(DisconnectCause cause)
    // {
    //     Debug.Log("연결끊김");
    // }

}



//     public void BtnCreateRoom(){      // 방만들기
//         if (PhotonNetwork.JoinLobby ()) { 
//             RoomOptions RO = new RoomOptions (); 
//             RO.MaxPlayers = 20;
//             //RO.IsOpen = false;  // false 못들어감 
//             // RO.IsVisible = !privateToggle.isOn; 
//             // roomName.text = "광주SSAFY";
//             if (RO.IsVisible) { 
//                 PhotonNetwork.CreateRoom ("광주SSAFY", RO, TypedLobby.Default); 
//                 Debug.Log("방만들어짐?");
//             } else { 
//                 PhotonNetwork.JoinOrCreateRoom ("광주SSAFY", RO, TypedLobby.Default); 
//         } 
//         } else { 
//             BtnCreateRoom ();   
//         } 
//     } 


//     // public void BtnJoinRoom(){ 
//     //     // PhotonNetwork.JoinRoom ("광주SSAFY");  // 방이름으로 방 들어가기
//     //     SceneManager.LoadScene("SampleScene");
//     // } 

//     public void BtnJoinRandomRoom(){ 
//         if (PhotonNetwork.JoinLobby ()) { 
//             PhotonNetwork.JoinRandomRoom ();       // 랜덤 방 들어가기
//         } else { 
//             BtnJoinRandomRoom ();   
//         } 
//     }

//     // public void JoinRoom()
//     // {   PhotonNetwork.CreateRoom("광주SSAFY");
//     //     Debug.Log("방 만들어짐?");
//     //     PhotonNetwork.JoinRoom("광주SSAFY");
//     //     Debug.Log("광주싸피 입장 완료");
//     // }
// }


