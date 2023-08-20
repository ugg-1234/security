package com.sopromadze.blogapi.infrastructure.persistence.mysql.adapter;

import com.sopromadze.blogapi.domain.model.Tag;
import com.sopromadze.blogapi.domain.model.TagBuilder;
import com.sopromadze.blogapi.infrastructure.persistence.mysql.entity.TagEntity;
import com.sopromadze.blogapi.infrastructure.persistence.mysql.entity.TagEntityBuilder;
import com.sopromadze.blogapi.infrastructure.persistence.mysql.mapper.TagEntityMapper;
import com.sopromadze.blogapi.infrastructure.persistence.mysql.repository.TagMysqlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagPersistenceMysqlAdapterTest {

    @Mock
    private TagMysqlRepository tagMysqlRepository;

    @Spy
    private TagEntityMapper tagEntityMapper = Mappers.getMapper(TagEntityMapper.class);

    @InjectMocks
    @Spy
    private TagPersistenceMysqlAdapter tagPersistenceMysqlAdapter;

    @Test
    void createTag_OK() {
        TagEntity entity = TagEntityBuilder.build();
        when(tagMysqlRepository.save(any(TagEntity.class))).thenReturn(entity);

        Tag tag = tagPersistenceMysqlAdapter.createTag(TagBuilder.build());

        assertThat(tag.getTagId()).isEqualTo(entity.getId().toString());
        assertThat(tag.getName()).isEqualTo(entity.getName());

        verify(tagMysqlRepository, times(1)).save(any(TagEntity.class));
        verify(tagEntityMapper, times(1)).toDomain(any());
    }
}