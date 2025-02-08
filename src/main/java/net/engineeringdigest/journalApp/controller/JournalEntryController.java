package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.servcice.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

//    private Map<Long,JournalEntry> journalEntries=new HashMap<>();
    @GetMapping("/journalList")
    public List<JournalEntry> getall(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
//        journalEntries.put(myEntry.getId(), myEntry);
        myEntry.setDateTime(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;

    }
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {

        return journalEntryService.findById(myId).orElse(null);

    }

    @DeleteMapping("id/{myId}")
    public boolean  deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;

    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry) {
        JournalEntry old=journalEntryService.findById(id).orElse(null);
        if(old !=null){
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            old.setTittle(newEntry.getTittle()!=null && !newEntry.getTittle().equals("")? newEntry.getTittle():old.getTittle());
        }
        journalEntryService.saveEntry(old);
        return old;

    }


}
