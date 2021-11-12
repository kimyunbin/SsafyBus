
/*
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RoomData : MonoBehaviour
{

    public string roomName = "";
    public int playerCount = 0;
    public int maxPlayer = 0;
        // Start is called before the first frame update

    [System.NonSerialized]
    public Text roomDataTxt;
    void Awake()
    {
        roomDataTxt = GetComponentInChidren<Text>();
    }

    public void UpdateInfo()
    {
        roomDataTxt.text = string.Format(" {0} [{1}/{2}]", roomName, playerCount.ToString("00"), maxPlayer);
    }
}

    // Update is called once per frame
    // void Update()
    // {
        
    // }

*/