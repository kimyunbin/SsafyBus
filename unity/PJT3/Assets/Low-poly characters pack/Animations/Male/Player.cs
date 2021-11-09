using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
using UnityEngine;

public class Player : MonoBehaviour
{
    float hAxis;
    float vAxis;
    Vector3 moveVec;
    Animator anim;
    // Start is called before the first frame update
    void Awake()
    {
        anim = GetComponentInChildren<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        string stageName = SceneManager.GetActiveScene().name;
        if(stageName == "Selection"){
            anim.SetBool("isSelect", true);
        }
        else{
            anim.SetBool("isSelect", true);
        }

        hAxis = Input.GetAxisRaw("Horizontal");
        vAxis = Input.GetAxisRaw("Vertical");


        moveVec = new Vector3(hAxis, 0, vAxis).normalized;
        anim.SetBool("isWalk", moveVec != Vector3.zero);
    }
}
