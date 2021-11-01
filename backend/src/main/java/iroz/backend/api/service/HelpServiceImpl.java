package iroz.backend.api.service;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.db.Mapping.CommentMapping;
import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Comment;
import iroz.backend.db.entity.Help;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.CommentRepository;
import iroz.backend.db.repository.HelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpServiceImpl implements HelpService{

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<HelpAllMapping> findAll(){
        return helpRepository.findAllBy();
    }

    @Override
    public void postHelp(User user, HelpPostReq helpPostReq){
        Help help = Help.builder().user(user).code(helpPostReq.getCode()).content(helpPostReq.getContent()).link(helpPostReq.getLink()).title(helpPostReq.getTitle()).build();
        helpRepository.save(help);
    }

    @Override
    public String deleteHelp(User user, Long help_pk){
        Help help = helpRepository.findById(help_pk).orElseThrow();
        if (user == help.getUser()){
            helpRepository.deleteById(help_pk);
            return "Success";
        }
        return "해당 게시물 작성 유저가 아닙니다.";
    }

    @Override
    public String putHelp(User user, Long help_pk, HelpPostReq helpPostReq){
        Help help = helpRepository.findById(help_pk).orElseThrow();
        if (user == help.getUser()){
            help.setCode(helpPostReq.getCode());
            help.setContent(helpPostReq.getContent());
            help.setLink(helpPostReq.getLink());
            help.setTitle(helpPostReq.getTitle());
            helpRepository.save(help);
            return "Success";
        }
        return "해당 게시물 작성 유저가 아닙니다.";
    }

    @Override
    public String postComment(User user, Long help_pk, String content){
        Help help = helpRepository.findById(help_pk).orElseThrow();
        Comment comment = Comment.builder().user(user).help(help).content(content).build();
        commentRepository.save(comment);
        return "Success";
    }

    @Override
    public Help getHelp(Long help_pk){
        Help help = helpRepository.findById(help_pk).orElseThrow();
        return help;
    }

    @Override
    public List<CommentMapping> getComments(Long help_pk){
        Help help = helpRepository.findById(help_pk).orElseThrow();
        return commentRepository.findAllByHelpId(help_pk);

    }
}
