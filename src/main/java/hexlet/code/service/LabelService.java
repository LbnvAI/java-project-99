package hexlet.code.service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelShowDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public final class LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper mapper;

    public List<LabelShowDTO> getAll() {
        List<Label> labels = labelRepository.findAll();
        return labels.stream().map(mapper::map).toList();
    }

    public LabelShowDTO getById(long id) {
        Label label = labelRepository.findById(id).orElseThrow();
        return mapper.map(label);
    }

    public LabelShowDTO create(LabelCreateDTO data) {
        Label label = mapper.map(data);
        labelRepository.save(label);
        return mapper.map(label);
    }

    public LabelShowDTO update(LabelUpdateDTO data, long id) {
        Label label = labelRepository.findById(id).orElseThrow();
        mapper.update(data, label);
        labelRepository.save(label);
        return mapper.map(label);
    }

    public void deleteById(long id) {
        labelRepository.deleteById(id);
    }
}
