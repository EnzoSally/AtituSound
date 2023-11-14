package br.edu.atitus.poo.atitusound.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.atitus.poo.atitusound.entities.GenericEntity;
import br.edu.atitus.poo.atitusound.repositories.GenericRepository;

public interface GenericService<TEntidade extends GenericEntity> {
	
	GenericRepository<TEntidade> getRepository();

	default void validate(TEntidade entity) throws Exception {
		if (entity.getName() == null || entity.getName().isEmpty())
			throw new Exception("BOTA SEU NOME AI MANO");
		if (entity.getUuid() == null) {
			if (getRepository().existsByName(entity.getName()))
				throw new Exception("JA TEM ESSE NOME MEU JOVEM");
		} else {
			if (!getRepository().existsById(entity.getUuid()))
				throw new Exception("NAO ENCONTREI ESSE UUID AI NAO EM");
			if (getRepository().existsByNameAndUuidNot(entity.getName(), entity.getUuid()))
				throw new Exception("JA TEM ESSE NOME MEU JOVEM");
		}
	}

	default TEntidade save(TEntidade entity) throws Exception {
		validate(entity);
		return getRepository().save(entity);
	}

	default List<TEntidade> findAll() throws Exception {
		return getRepository().findAll();
	}

	default Page<List<TEntidade>> findByName(Pageable pageable, String name) throws Exception {
		return getRepository().findByNameContainingIgnoreCase(pageable, name);
	}

	default Optional<TEntidade> findById(UUID uuid) {
		return getRepository().findById(uuid);
	}

	default void deleteById(UUID uuid) throws Exception {
		if (!getRepository().existsById(uuid))
			throw new Exception("NAO EXISTE REGISTRO ASSIM MEU");
		getRepository().deleteById(uuid);
	}

}
