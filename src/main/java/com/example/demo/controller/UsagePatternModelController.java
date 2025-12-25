@RestController
@RequestMapping("/api/models")
public class UsagePatternModelController {

    private final UsagePatternModelService modelService;

    public UsagePatternModelController(UsagePatternModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<UsagePatternModelDTO> createModel(
            @Valid @RequestBody UsagePatternModelDTO dto) {
        return new ResponseEntity<>(modelService.createModel(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsagePatternModelDTO> updateModel(
            @PathVariable Long id,
            @Valid @RequestBody UsagePatternModelDTO dto) {
        return ResponseEntity.ok(modelService.updateModel(id, dto));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<UsagePatternModelDTO> getModelForBin(@PathVariable Long binId) {
        return ResponseEntity.ok(modelService.getModelForBin(binId));
    }

    @GetMapping
    public ResponseEntity<List<UsagePatternModelDTO>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }
}
