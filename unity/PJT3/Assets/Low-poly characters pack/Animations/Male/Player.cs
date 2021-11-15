using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
using UnityEngine;
using Photon;
public class Player : Photon.PunBehaviour
{
    public float speed = 1f;
    float hAxis;
    float vAxis;

    private Transform tr;
    Vector3 moveVec;
    Animator anim;
    public float rotateSpeed = 3.3f; // 좌우 회전 속도
    // Start is called before the first frame update
    void Awake()
    {
        anim = GetComponentInChildren<Animator>();
    }

    // Update is called once per frame
    void Start()
    {
        tr = GetComponent<Transform>();
        if(photonView.isMine){
        // Camera.main.GetComponent<SmoothFollow>().target = tr.Find("CamPivot").transform;
        }
    }
    void Update()
    {
        if(!photonView.isMine){
            return;
        }
        string stageName = SceneManager.GetActiveScene().name;
        if(stageName == "Selection"){
            anim.SetBool("isSelect", true);
        }
        else{
            anim.SetBool("isSelect", true);
        }

        hAxis = Input.GetAxisRaw("Horizontal");
        vAxis = Input.GetAxisRaw("Vertical");

        // speed = 2;

        moveVec = new Vector3(hAxis, 0, vAxis).normalized;
        // transform.position += moveVec * speed * Time.deltaTime;
        transform.Translate(Vector3.forward * speed * vAxis * Time.deltaTime);
        anim.SetBool("isWalk", moveVec != Vector3.zero);

        // transform.LookAt(transform.position + moveVec);
        transform.Rotate(Vector3.up * rotateSpeed * hAxis); 
    }
}
