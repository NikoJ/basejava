package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializableStrategy serializations;

    protected PathStorage(String dir, SerializableStrategy serializations) {
        this.serializations = serializations;
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or isn't writable");
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            serializations.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path update error: ", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path save error: ", null, e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializations.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path get error: ", null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getStreamPath().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Path getPosition(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public void clear() {
        getStreamPath().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getStreamPath().count();
    }

    private Stream<Path> getStreamPath() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path error", null, e);
        }
    }
}
