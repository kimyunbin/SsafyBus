using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
using UnityEngine;

public class Player : MonoBehaviour
{
    public float speed = 1f;
    float hAxis;
    float vAxis;
    Vector3 moveVec;
    Animator anim;
    public float rotateSpeed = 3.3f; // 좌우 회전 속도
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

        // speed = 2;

        moveVec = new Vector3(hAxis, 0, vAxis).normalized;
        // transform.position += moveVec * speed * Time.deltaTime;
        transform.Translate(Vector3.forward * speed * vAxis * Time.deltaTime);
        anim.SetBool("isWalk", moveVec != Vector3.zero);

        // transform.LookAt(transform.position + moveVec);
        transform.Rotate(Vector3.up * rotateSpeed * hAxis); 
    }
}
