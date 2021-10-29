package iroz.backend.api.service;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Help;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.HelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpServiceImpl implements HelpService{

    @Autowired
    private HelpRepository helpRepository;

    @Override
    public List<HelpAllMapping> findAll(){
        return helpRepository.findAllBy();
    }

    @Override
    public void postHelp(User user, HelpPostReq helpPostReq){
        Help help = Help.builder().user(user).code(helpPostReq.getCode()).content(helpPostReq.getContent()).link(helpPostReq.getLink()).title(helpPostReq.getTitle()).build();
        helpRepository.save(help);
    }
}
