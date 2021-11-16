using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Photon;
using UnityEngine.UI;

public class Chatting : Photon.PunBehaviour
{

    public Text[] ChatText;
    public InputField ChatInput;

    // public static readonly string ChatRPC = "Chat";

    public PhotonView PV;
    // Start is called before the first frame update
    void Start()
    {
        ChatInput.text = "";
        for (int i = 0; i < ChatText.Length; i++) ChatText[i].text = "";
        
    }




    // Update is called once per frame
    public void Send()
    {

        // PhotonView pv = this.gameObject.GetPhotonView();
        if (PV == null)
        {
            Debug.LogError("Can't do manual instantiation without PhotonView component.");
            return;
        }
        else {
            PV.RPC("ChatRPC", PhotonTargets.All, PhotonNetwork.playerName + " : " + ChatInput.text);
            ChatInput.text = "";
        }






        // PhotonView pv = this.gameObject.GetPhotonView();
        // if (pv == null)
        // {
        //     Debug.LogError("Can't do manual instantiation without PhotonView component.");
        //     return;
        // }

        // int viewID = PhotonNetwork.AllocateViewID();
        // pv.RPC("InstantiateRpc", PhotonTargets.AllBuffered, viewID);
    }

    [PunRPC] // RPC는 플레이어가 속해있는 방 모든 인원에게 전달한다
    void ChatRPC(string msg)
    {
        bool isInput = false;
        for (int i = 0; i < ChatText.Length; i++)
            if (ChatText[i].text == "")
            {
                isInput = true;
                ChatText[i].text = msg;
                break;
            }
        if (!isInput) // 꽉차면 한칸씩 위로 올림
        {
            for (int i = 1; i < ChatText.Length; i++) ChatText[i - 1].text = ChatText[i].text;
            ChatText[ChatText.Length - 1].text = msg;
        }
    }
    
}
