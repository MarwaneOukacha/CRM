package com.crm.product.dao.impl;

import com.crm.product.dao.ProductDao;
import com.crm.product.entities.*;
import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;
    private final DesignerRepository designerRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final OccasionRepository occasionRepository;

    @Override
    public Product save(Product product) {
        log.info("ProductDaoImpl::save - Saving product: {}", product);
        Product saved = productRepository.save(product);
        log.info("ProductDaoImpl::save - Saved product with id: {}", saved.getId());
        return saved;
    }

    @Override
    public Product findById(UUID id) {
        log.info("ProductDaoImpl::findById - Finding product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        log.info("ProductDaoImpl::findById - Found product: {}", product);
        return product;
    }

    @Override
    public Page<Product> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable) {
        log.info("ProductDaoImpl::findAllWithCriteria - Searching products with criteria: {}", criteria);
        Specification<Product> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String kw = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), kw);
                Predicate descPredicate = cb.like(cb.lower(root.get("description")), kw);
                predicates.add(cb.or(namePredicate, descPredicate));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), criteria.getCategory()));
            }

            if (criteria.getMaterial() != null && !criteria.getMaterial().isEmpty()) {
                // Assuming productMaterials join exists and material name is matched
                predicates.add(cb.equal(root.join("productMaterials").get("material").get("name"), criteria.getMaterial()));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(cb.ge(root.get("price"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.le(root.get("price"), criteria.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> result = productRepository.findAll(specification, pageable);
        log.info("ProductDaoImpl::findAllWithCriteria - Found {} products", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("ProductDaoImpl::delete - Deleting product with id: {}", id);
        productRepository.deleteById(id);
        log.info("ProductDaoImpl::delete - Deleted product with id: {}", id);
    }

    @Override
    public Product updateProduct(UUID id, Product updatedProduct) {
        log.info("ProductDaoImpl::updateProduct - Updating product with id: {}", id);
        Product existing = findById(id);

        // Update fields only if not null to avoid overwriting existing values with null
        if (updatedProduct.getName() != null) existing.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getCode() != null) existing.setCode(updatedProduct.getCode());
        if (updatedProduct.getClicks() != 0) existing.setClicks(updatedProduct.getClicks());
        if (updatedProduct.getFavorite() != 0) existing.setFavorite(updatedProduct.getFavorite());
        if (updatedProduct.getCart() != 0) existing.setCart(updatedProduct.getCart());
        if (updatedProduct.getSize() != 0) existing.setSize(updatedProduct.getSize());
        if (updatedProduct.getCarat() != null) existing.setCarat(updatedProduct.getCarat());
        if (updatedProduct.getStatus() != null) existing.setStatus(updatedProduct.getStatus());
        if (updatedProduct.getCategory() != null) existing.setCategory(updatedProduct.getCategory());
        if (updatedProduct.getAgencyId() != null) existing.setAgencyId(updatedProduct.getAgencyId());
        if (updatedProduct.getType() != null) existing.setType(updatedProduct.getType());
        if (updatedProduct.getWeight() != null) existing.setWeight(updatedProduct.getWeight());
        if (updatedProduct.getPartnerId() != null) existing.setPartnerId(updatedProduct.getPartnerId());

        // Collections (productOccasion, productMaterials, productColors, productDesigners, media)
        // should be handled separately in service layer if complex logic is needed

        Product saved = productRepository.save(existing);
        log.info("ProductDaoImpl::updateProduct - Updated product with id: {}", saved.getId());
        return saved;
    }

    @Override
    public List<ProductDesigner> findDesignersByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        List<UUID> uuidList = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        List<Designer> designers = designerRepository.findAllById(uuidList);

        return designers.stream()
                .map(designer -> {
                    ProductDesigner pd = new ProductDesigner();
                    pd.setDesigner(designer); // ✅ Set designer
                    return pd;                // product will be set by caller
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductColor> findColorsByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        List<UUID> uuidList = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        List<Color> colors = colorRepository.findAllById(uuidList);

        return colors.stream()
                .map(color -> {
                    ProductColor pc = new ProductColor();
                    pc.setColor(color); // ✅ Set color
                    return pc;          // product will be set by caller
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductMaterial> findMaterialsByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        List<UUID> uuidList = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        List<Material> materials = materialRepository.findAllById(uuidList);

        return materials.stream()
                .map(material -> {
                    ProductMaterial pm = new ProductMaterial();
                    pm.setMaterial(material); // ✅ Set material
                    return pm;                // product will be set by caller
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductOccasion> findOccasionsByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        List<UUID> uuidList = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        List<Occasion> occasions = occasionRepository.findAllById(uuidList);

        // Ensure each ProductOccasion has its Occasion set, and product is left for the caller
        return occasions.stream()
                .map(occasion -> {
                    ProductOccasion po = new ProductOccasion();
                    po.setOccasion(occasion); // Must be set
                    return po; // product will be set later
                })
                .collect(Collectors.toList());
    }


}
