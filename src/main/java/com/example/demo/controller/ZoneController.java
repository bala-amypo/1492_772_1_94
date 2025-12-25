@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public ResponseEntity<ZoneDTO> createZone(@Valid @RequestBody ZoneDTO zoneDTO) {
        ZoneDTO created = zoneService.createZone(zoneDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDTO> updateZone(
            @PathVariable Long id, @Valid @RequestBody ZoneDTO zoneDTO) {
        return ResponseEntity.ok(zoneService.updateZone(id, zoneDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDTO> getZoneById(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @GetMapping
    public ResponseEntity<List<ZoneDTO>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateZone(@PathVariable Long id) {
        zoneService.deactivateZone(id);
        return ResponseEntity.noContent().build();
    }
}
