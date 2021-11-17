using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
// using Photon;
using Photon.Pun;
using Photon.Realtime;
using UnityEngine.SceneManagement;


public class Launcher :  MonoBehaviourPunCallbacks {
    private readonly string gameVersion = "1.0";
    public Text StatusText;
    public GameObject EmptyRoom;
    public GameObject Room;
    public Text RoomInfoText;


    public Text roomName;
    public Text playerLength;


    // void Update() => StatusText.text = PhotonNetwork.networkingPeer.State.ToString(); //현재 어떤 상태인지

    // 시작 -------------------------------------------
    void Start()
    {
        Debug.Log("마스터 서버 연결 완료");
        // PhotonNetwork.ConnectUsingSettings("1.0"); // 내가 설정한 포톤서버 마스터 서버 연결
        PhotonNetwork.GameVersion = gameVersion;
        // 설정 정보를 가지고 마스터 서버에 접속 시도
        PhotonNetwork.ConnectUsingSettings();
        // PhotonNetwork.ConnectUsingSettings("1.0"); // 내가 설정한 포톤서버 마스터 서버 연결
        // PhotonNetwork.ConnectToMaster("http://localhost:8080/", int port, string appID, string gameVersion)
        // PhotonNetwork.ConnectToMaster("http://localhost", 8080, "ffab2165-f864-4fc0-9e9a-9ef945691bc7", "1");

        // Debug.Log(PhotonNetwork.ConnectUsingSettings("1.0"));
        Debug.Log("----------------------------------------");
    }


    void Update() {
        StatusText.text =  PhotonNetwork.NetworkClientState.ToString();
        
    }

    // public void Connect() => PhotonNetwork.ConnectUsingSettings("0.1");

    //     void Update()
    // {
    //     StatusText.text = PhotonNetwork.NetworkClientState.ToString();
    //     LobbyInfoText.text = (PhotonNetwork.CountOfPlayers - PhotonNetwork.CountOfPlayersInRooms) + "로비 / " + PhotonNetwork.CountOfPlayers + "접속";
    // }

    // public void OnConnectedToPhoton()
    // {
    //     Debug.Log("ㅎㅇㅎㅇㅎㅇㅎㅇ");
    // }
    // public void OnDisconnectedFromPhoton()
    // {
    //     Debug.Log("OnDisconnectedFromPhoton");   
    // }
    // public override void OnConnectionFail(DisconnectCause cause)
    // {
    //     Debug.Log("OnConnectionFail");
    // }
    //     public void OnFailedToConnectToPhoton()
    // {
    //     Debug.Log("OnFailedToConnectToPhoton");
    // }
    //     // 수동으로 메인에 있으면 조인룸 및 예외처리
    // public void Connect()
    // {
    //     Debug.Log("6666666");

    //     // if (PhotonNetwork.IsConnected)
    //     // {
    //     //     Debug.Log("0000000000000");
    //     // }
    //     // else
    //     // {
    //     //     Debug.Log("6666666");
    //     // }
    // }



    // 마스터 서버에 접속 성공했을 때 
    public override void OnConnectedToMaster()
    {
        Debug.Log("마스터 서버로 접속 완료");
        PhotonNetwork.LocalPlayer.NickName = "닉네임 임의로 설정";
        PhotonNetwork.JoinLobby();
        PhotonNetwork.AutomaticallySyncScene = true;
    }

    // public override void OnDisconnected()
    // {
    //     connectionBtn.interactable = false;
    //     connectionInfoText.text = $"Offline: No connection with the master server\n {cause.ToString()}";

    //     PhotonNetwork.ConnectUsingSettings("1.0");
    // }

    //ㅏ로비까지 들어옴
    public override void OnJoinedLobby()
    {
        Debug.Log("로비 들어옴");
    }


    // 방 여부 따라 패널 다른 거 ---------------------------
    public void SetPanel(){
 
        // 방이 없으면 방만들기 패널 활성화
        if ( PhotonNetwork.CountOfRooms == 0){
            EmptyRoom.SetActive(true); //방만들기 패널
        }

        // 방이 있으면 입장 버튼 패널만 활성화
        else {
            Room.SetActive(true); //입장버튼 패널
            // Debug.Log(PhotonNetwork.GetRoomList() );
            // Debug.Log(PhotonNetwork.GetRoomList().Length );
            // Debug.Log(PhotonNetwork.room.name );
            // Debug.Log(PhotonNetwork.room.PlayerCount);
            // Debug.Log(PhotonNetwork.room.MaxPlayers);
        // Debug.Log();
            // playerLength.text = PhotonNetwork.room.name + " / " + PhotonNetwork.room.PlayerCount + "명 / " + PhotonNetwork.room.MaxPlayers + "최대";

        }
    }
    

    // 방만들기 ------------------------------------------
    public void CreateRoom(){

        // string maxPlayer = "15";

        string RoomName = "광주";
        // UnityroomHook(RoomName);
        PhotonNetwork.CreateRoom(RoomName, new RoomOptions { MaxPlayers = 15});
      
 
    }
    
    public override void OnCreatedRoom() {

        Debug.Log("방 만들기 완료");
        Debug.Log(PhotonNetwork.CurrentRoom);
        // Debug.Log(PhotonNetwork.GetRoomList().Length);
        
        RoomInfoText.text = PhotonNetwork.CurrentRoom.Name.ToString();
        Debug.Log(RoomInfoText.text);

        // 여기서 바로 씬 변경 이뤄지면 될듯?
        SceneManager.LoadScene("SampleScene");
        // PhotonNetwork.room.IsVisible = false;//게임시작하면 안보임
        // PhotonNetwork.LoadLevel(1);
    }


    // 입장버튼 누르면 광주로 입장
    public void ChangeScene(){
        PhotonNetwork.JoinRoom("광주");       
    }

    public override void OnJoinedRoom() {
        
        Debug.Log(playerLength.text);
        Debug.Log(PhotonNetwork.CurrentRoom.Name );
        Debug.Log(PhotonNetwork.CurrentRoom.PlayerCount);
        Debug.Log(PhotonNetwork.CurrentRoom.MaxPlayers);
        // Debug.Log();
        Debug.Log("방 참가 완료");

        SceneManager.LoadScene("SampleScene");
        // PhotonNetwork.Instantiate("ThisrdPersonController", new Vector3(0,3.0f,0), Quaternion.identity, 0);
        // PhotonNetwork.room.IsVisible = false;//게임시작하면 안보임
        // PhotonNetwork.LoadLevel(1);
    }



    public void UserName(string initName)
    {
        if (initName!=null)
        {
            PhotonNetwork.NickName = initName;
            // connectionBtn.interactable = true;
        }
    }








    // 방만들고입장-------------------------------------------------------------------------------
    public void JoinOrCreateRoom(){
        // PhotonNetwork.JoinOrCreateRoom("광주", new RoomOptions { MaxPlayers = 15 }, null);
        // SceneManager.LoadScene("SampleScene");
        Debug.Log(PhotonNetwork.CurrentRoom);
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

    // public void OnDisconnected(DisconnectCause cause)
    // {
    //     Debug.Log("연결끊김");
    // }





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


}