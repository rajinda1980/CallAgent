package com.codechallenge.callapp.configuration;

import com.codechallenge.callapp.dto.Location;
import com.codechallenge.callapp.dto.TransferLocations;
import com.codechallenge.callapp.dto.TransferType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    private Map<String, List<Location>> locationMap = null;
    private List<String> transferTypeNames;

    @Value("${agent.transfer_type.names}")
    private String[] transferTypeNames_props;

    public Map<String, List<Location>> getLocations() throws Exception {
        if (null == locationMap) {
            locationMap = loadLocationMap();
        }
        return locationMap;
    }

    public List<String> getTransferTypeNames() throws Exception {
        if (null == transferTypeNames) {
            transferTypeNames = Arrays.asList(transferTypeNames_props);
        }
        return transferTypeNames;
    }

    private Map<String, List<Location>> loadLocationMap() throws Exception {
        try {
            Resource resource = new ClassPathResource("locations.xml");
            File file = resource.getFile();
            JAXBContext jaxbContext = JAXBContext.newInstance(TransferLocations.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TransferLocations locations = (TransferLocations) unmarshaller.unmarshal(file);
            locationMap = locations.getTransferType().stream()
                    .map(il -> {
                        List<Location> location =
                                il.getLocation().stream()
                                        .sorted(Comparator.comparing(Location::getWeight).reversed())
                                        //.filter(l -> l.isEnabled() == true)
                                        .collect(Collectors.toCollection(LinkedList::new));
                        il.setLocation(location);
                        return il;
                    })
                    .collect(Collectors.toMap(
                            TransferType::getName,
                            TransferType::getLocation));
            return locationMap;

        } catch (Exception e) {
            System.out.println("Cannot read location details from xml. ERROR : " + e.getMessage());
            throw new Exception(e);
        }
    }

    public boolean updateConfig(String location_name) throws Exception {
        Map<String, List<Location>> locMap = getLocations();
        boolean foundItem = false;

        Iterator itr = locMap.keySet().iterator();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            List<Location> locations = locMap.get(key);
            for (Location loc : locations) {
                if (loc.getName().equalsIgnoreCase(location_name.strip())) {
                    loc.setEnabled(!loc.isEnabled());
                    foundItem = true;
                    break;
                }
            }
        }

        return foundItem;
    }
}
