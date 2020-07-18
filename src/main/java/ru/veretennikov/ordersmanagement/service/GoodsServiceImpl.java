package ru.veretennikov.ordersmanagement.service;

import org.springframework.stereotype.Service;
import ru.veretennikov.ordersmanagement.domain.Goods;
import ru.veretennikov.ordersmanagement.repository.GoodsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository repository;

    public GoodsServiceImpl(GoodsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Goods> getAllGoods() {
        return repository.findAll();
    }


    @Override
    public Optional<Goods> getGoodsById(Integer id) {
        return repository.findById(id);
    }

}
